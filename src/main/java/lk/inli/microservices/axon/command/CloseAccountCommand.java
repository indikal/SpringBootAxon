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
public class CloseAccountCommand {

    @TargetAggregateIdentifier
    public final String id;

    public CloseAccountCommand(String id) {
        this.id = id;
    }
}
