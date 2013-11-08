/**
 * Copyright (c) 2002-2013 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cluster.protocol.heartbeat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.concurrent.Executor;

import org.junit.Test;
import org.mockito.Matchers;
import org.neo4j.cluster.InstanceId;
import org.neo4j.cluster.com.message.Message;
import org.neo4j.cluster.com.message.MessageHolder;
import org.neo4j.cluster.protocol.atomicbroadcast.ObjectInputStreamFactory;
import org.neo4j.cluster.protocol.atomicbroadcast.ObjectOutputStreamFactory;
import org.neo4j.cluster.protocol.atomicbroadcast.multipaxos.LearnerContext;
import org.neo4j.cluster.protocol.atomicbroadcast.multipaxos.ProposerContext;
import org.neo4j.cluster.protocol.cluster.ClusterConfiguration;
import org.neo4j.cluster.protocol.cluster.ClusterContext;
import org.neo4j.cluster.timeout.Timeouts;
import org.neo4j.helpers.collection.Iterables;
import org.neo4j.kernel.impl.util.StringLogger;
import org.neo4j.kernel.logging.Logging;

public class HeartbeatStateTest
{
    @Test
    public void shouldIgnoreSuspicionsForOurselves() throws Throwable
    {
        // Given
        InstanceId instanceId = new InstanceId( 1 );
        HeartbeatState heartbeat= HeartbeatState.heartbeat;
        ClusterConfiguration configuration = new ClusterConfiguration("whatever", "cluster://1", "cluster://2" );
        configuration.joined( instanceId, URI.create("cluster://1" ) );
        configuration.joined( new InstanceId( 2 ), URI.create("cluster://2" ));

        Logging logging = mock( Logging.class );
        ClusterContext clusterContext = new ClusterContext( instanceId, mock( ProposerContext.class ),
                mock( LearnerContext.class), configuration, mock( Timeouts.class ), mock( Executor.class ),
                logging, mock( ObjectInputStreamFactory.class), mock( ObjectOutputStreamFactory.class) );

        when( logging.getMessagesLog( Matchers.<Class>any() ) ).thenReturn( mock( StringLogger.class ) );

        HeartbeatContext context = new HeartbeatContext( clusterContext, mock( LearnerContext.class ), mock( Executor.class) );
        Message received = Message.internal( HeartbeatMessage.suspicions,
                new HeartbeatMessage.SuspicionsState( Iterables.toSet( Iterables.<InstanceId, InstanceId>iterable( instanceId ) ) ) );
        received.setHeader( Message.FROM, "cluster://2" );

        // When
        heartbeat.handle( context, received, mock( MessageHolder.class) );

        // Then
        assertThat( context.getSuspicionsOf( instanceId ).size(), equalTo( 0 ) );
    }

    @Test
    public void shouldIgnoreSuspicionsForOurselvesButKeepTheRest() throws Throwable
    {
        // Given
        InstanceId myId = new InstanceId( 1 );
        InstanceId foreignId = new InstanceId( 3 );
        HeartbeatState heartbeat= HeartbeatState.heartbeat;
        ClusterConfiguration configuration = new ClusterConfiguration("whatever", "cluster://1", "cluster://2" );
        configuration.joined( myId, URI.create("cluster://1" ) );
        configuration.joined( new InstanceId( 2 ), URI.create("cluster://2" ));

        Logging logging = mock( Logging.class );
        ClusterContext clusterContext = new ClusterContext( myId, mock( ProposerContext.class ),
                mock( LearnerContext.class), configuration, mock( Timeouts.class ), mock( Executor.class ),
                logging, mock( ObjectInputStreamFactory.class), mock( ObjectOutputStreamFactory.class) );

        when( logging.getMessagesLog( Matchers.<Class>any() ) ).thenReturn( mock( StringLogger.class ) );

        HeartbeatContext context = new HeartbeatContext( clusterContext, mock( LearnerContext.class ), mock( Executor.class) );
        Message received = Message.internal( HeartbeatMessage.suspicions,
                new HeartbeatMessage.SuspicionsState( Iterables.toSet( Iterables.<InstanceId, InstanceId>iterable( myId, foreignId ) ) ) );
        received.setHeader( Message.FROM, "cluster://2" );

        // When
        heartbeat.handle( context, received, mock( MessageHolder.class) );

        // Then
        assertThat( context.getSuspicionsOf( myId ).size(), equalTo( 0 ) );
        assertThat( context.getSuspicionsOf( foreignId ).size(), equalTo( 1 ) );
    }
}
