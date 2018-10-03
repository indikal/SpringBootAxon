/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.inli.microservices.axon;

import java.io.Serializable;
import lk.inli.microservices.axon.command.CloseAccountCommand;
import lk.inli.microservices.axon.command.CreateAccountCommand;
import lk.inli.microservices.axon.command.DepositMoneyCommand;
import lk.inli.microservices.axon.command.WithdrawMoneyCommand;
import lk.inli.microservices.axon.event.AccountClosedEvent;
import lk.inli.microservices.axon.event.AccountCreatedEvent;
import lk.inli.microservices.axon.event.MoneyDepositedEvent;
import lk.inli.microservices.axon.event.MoneyWithdrawnEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

/**
 *
 * @author indika
 */
@Aggregate
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @AggregateIdentifier
    private String id;

    private double balance;

    private String owner;

    public BankAccount() {
        // constructor needed for reconstruction
    }

    @CommandHandler
    public BankAccount(CreateAccountCommand command) {
        String id = command.id;
        String creator = command.accountCreator;

        Assert.hasLength(id, "Missing id");
        Assert.hasLength(creator, "Missing account creator");

        AggregateLifecycle.apply(new AccountCreatedEvent(id, creator, 0));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent event) {
        this.id = event.id;
        this.owner = event.accountCreator;
        this.balance = event.balance;
    }

    @CommandHandler
    protected void on(DepositMoneyCommand command) {
        double amount = command.amount;
        Assert.isTrue(amount > 0.0, "Deposit must be a positiv number.");

        AggregateLifecycle.apply(new MoneyDepositedEvent(id, amount));
    }

    @EventSourcingHandler
    protected void on(MoneyDepositedEvent event) {
        this.balance += event.amount;
    }

    @CommandHandler
    protected void on(WithdrawMoneyCommand command) {
        double amount = command.amount;

        Assert.isTrue(amount > 0.0, "Withdraw must be a positiv number.");

        if (balance - amount < 0) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }

        AggregateLifecycle.apply(new MoneyWithdrawnEvent(id, amount));
    }

    @EventSourcingHandler
    protected void on(MoneyWithdrawnEvent event) {
        this.balance -= event.amount;
    }

    @CommandHandler
    protected void on(CloseAccountCommand command) {
        AggregateLifecycle.apply(new AccountClosedEvent(id));
    }

    @EventSourcingHandler
    protected void on(AccountClosedEvent event) {
        AggregateLifecycle.markDeleted();
    }

    public double getBalance() {
        return balance;
    }

    public static class InsufficientBalanceException extends RuntimeException {

        InsufficientBalanceException(String message) {
            super(message);
        }
    }
}
