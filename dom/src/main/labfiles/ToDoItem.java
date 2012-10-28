/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package dom.todo;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.jdo.spi.PersistenceCapable;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberGroups;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Resolve;
import org.apache.isis.applib.annotation.Resolve.Type;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.runtimes.dflt.objectstores.jdo.applib.annotations.Auditable;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
@javax.jdo.annotations.Queries( {
    @javax.jdo.annotations.Query(
        name="todo_notYetDone", language="JDOQL",  
        value="SELECT FROM dom.todo.ToDoItem WHERE ownedBy == :ownedBy && done == false"),
    @javax.jdo.annotations.Query(
            name="todo_done", language="JDOQL",  
            value="SELECT FROM dom.todo.ToDoItem WHERE ownedBy == :ownedBy && done == true"),
    @javax.jdo.annotations.Query(
        name="todo_similarTo", language="JDOQL",  
        value="SELECT FROM dom.todo.ToDoItem WHERE ownedBy == :ownedBy && category == :category"),
    @javax.jdo.annotations.Query(
            name="todo_autoComplete", language="JDOQL",  
            value="SELECT FROM dom.todo.ToDoItem WHERE ownedBy == :ownedBy && description.startsWith(:description)")
})
@javax.jdo.annotations.Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION")
@ObjectType("TODO")
@Auditable
@AutoComplete(repository=ToDoItems.class, action="autoComplete")
public class ToDoItem {
    
    public static enum Category {
        Professional, Domestic, Other;
    }

    // {{ Description
    private String description;

    public String getDescription() {
        return description;
    }
    public void setDescription(final String description) {
        this.description = description;
    }
    // }}

    // {{ Category
    private Category category;
    public Category getCategory() {
        return category;
    }
    public void setCategory(final Category category) {
        this.category = category;
    }
    // }}

    // {{ DueBy (property)
    private LocalDate dueBy;
    @javax.jdo.annotations.Persistent
    public LocalDate getDueBy() {
        return dueBy;
    }
    public void setDueBy(final LocalDate dueBy) {
        this.dueBy = dueBy;
    }
    // }}

    // {{ Done
    private boolean done;
    public boolean getDone() {
        return done;
    }
    public void setDone(final boolean done) {
        this.done = done;
    }
    // }}

    // {{ Notes (property)
    private String notes;
    public String getNotes() {
        return notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }
    // }}

    // {{ OwnedBy (property, hidden)
    private String ownedBy;
    public String getOwnedBy() {
        return ownedBy;
    }
    public void setOwnedBy(final String ownedBy) {
        this.ownedBy = ownedBy;
    }
    // }}

    // {{ injected: DomainObjectContainer
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    public void setDomainObjectContainer(final DomainObjectContainer container) {
        this.container = container;
    }
    // }}


    // {{ injected: ToDoItems
    @SuppressWarnings("unused")
    private ToDoItems toDoItems;

    public void setToDoItems(final ToDoItems toDoItems) {
        this.toDoItems = toDoItems;
    }
    // }}
   
}
