/**
 * Copyright (c) 2002-2015 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.kernel.impl.util.dbstructure;

import org.neo4j.helpers.collection.Visitable;
import org.neo4j.kernel.impl.util.dbstructure.DbStructureVisitor;

import org.neo4j.kernel.api.constraints.UniquenessConstraint;
import org.neo4j.kernel.api.index.IndexDescriptor;

//
// GENERATED FILE. DO NOT EDIT. 
//
// This has been generated by:
//
//   org.neo4j.kernel.impl.util.dbstructure.DbStructureTool org.neo4j.kernel.impl.util.dbstructure.QMULDbStructure [<output source root>] <db-dir>
//
// (using org.neo4j.kernel.impl.util.dbstructure.InvocationTracer)
//

public enum QMULDbStructure
implements Visitable<DbStructureVisitor>
{
    INSTANCE;

    public void accept( DbStructureVisitor visitor )
    {
        visitor.visitLabel( 1, "Person" );
        visitor.visitPropertyKey( 0, "name" );
        visitor.visitPropertyKey( 1, "location" );
        visitor.visitPropertyKey( 2, "uid" );
        visitor.visitPropertyKey( 3, "link" );
        visitor.visitPropertyKey( 4, "gender" );
        visitor.visitPropertyKey( 5, "email" );
        visitor.visitPropertyKey( 6, "education" );
        visitor.visitPropertyKey( 7, "work" );
        visitor.visitPropertyKey( 8, "node_type" );
        visitor.visitPropertyKey( 9, "hometown" );
        visitor.visitPropertyKey( 10, "birthdate" );
        visitor.visitPropertyKey( 11, "ids" );
        visitor.visitPropertyKey( 12, "created" );
        visitor.visitPropertyKey( 13, "since" );
        visitor.visitPropertyKey( 14, "location_name" );
        visitor.visitPropertyKey( 15, "location_lon" );
        visitor.visitPropertyKey( 17, "location_id" );
        visitor.visitPropertyKey( 16, "location_lat" );
        visitor.visitRelationshipType( 0, "friends" );
        visitor.visitRelationshipType( 1, "FRIEND" );
        visitor.visitIndex( new IndexDescriptor( 1, 2 ), ":Person(uid)", 1.0d );
        visitor.visitAllNodesCount( 135242l );
        visitor.visitNodeCount( 1, "Person", 135213l );
        visitor.visitRelCount( -1, -1, -1, "MATCH ()-[]->() RETURN count(*)", 4537616l );
        visitor.visitRelCount( 1, -1, -1, "MATCH (:Person)-[]->() RETURN count(*)", 4536688l );
        visitor.visitRelCount( -1, -1, 1, "MATCH ()-[]->(:Person) RETURN count(*)", 4536688l );
        visitor.visitRelCount( -1, 0, -1, "MATCH ()-[:friends]->() RETURN count(*)", 4537616l );
        visitor.visitRelCount( 1, 0, -1, "MATCH (:Person)-[:friends]->() RETURN count(*)", 4536688l );
        visitor.visitRelCount( -1, 0, 1, "MATCH ()-[:friends]->(:Person) RETURN count(*)", 4536688l );
        visitor.visitRelCount( -1, 1, -1, "MATCH ()-[:FRIEND]->() RETURN count(*)", 0l );
        visitor.visitRelCount( 1, 1, -1, "MATCH (:Person)-[:FRIEND]->() RETURN count(*)", 0l );
        visitor.visitRelCount( -1, 1, 1, "MATCH ()-[:FRIEND]->(:Person) RETURN count(*)", 0l );
   }
}

/* END OF GENERATED CONTENT */
