/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.inli.microservices.axon.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 *
 * @author indika
 */
public class CreateAccountCommand {

    @TargetAggregateIdentifier
    public final String id;
    public final String accountCreator;

    public CreateAccountCommand(String id, String accountCreator) {
        this.id = id;
        this.accountCreator = accountCreator;
    }
}
