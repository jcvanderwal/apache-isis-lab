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

import java.util.List;

import com.google.common.base.Objects;

import dom.todo.ToDoItem.Category;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.filter.Filter;

@Named("ToDos")
public class ToDoItems extends AbstractFactoryAndRepository {

    // {{ Id, iconName
    @Override
    public String getId() {
        return "toDoItems";
    }

    public String iconName() {
        return "ToDoItem";
    }
    // }}

    // {{ NotYetDone (action)
    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "1")
    public List<ToDoItem> notYetDone() {
        return allMatches(ToDoItem.class, new Filter<ToDoItem>() {
            @Override
            public boolean accept(final ToDoItem t) {
                return ownedByCurrentUser(t) && !t.getDone();
            }
        });
    }
    // }}

    // {{ Done (action)
    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "2")
    public List<ToDoItem> done() {
        return allMatches(ToDoItem.class, new Filter<ToDoItem>() {
            @Override
            public boolean accept(final ToDoItem t) {
                return ownedByCurrentUser(t) && t.getDone();
            }
        });
    }
    // }}


    // {{ newToDo  (action)
    @MemberOrder(sequence = "2")
    public ToDoItem newToDo(
            @Named("Description") String description, 
            @Named("Category") Category category) {
        final String ownedBy = getContainer().getUser().getName();
        return newToDo(description, category, ownedBy);
    }
    // }}

    // {{ newToDo  (hidden)
    @Hidden // for use by fixtures
    public ToDoItem newToDo(
            String description, 
            Category category, 
            String ownedBy) {
        final ToDoItem toDoItem = newTransientInstance(ToDoItem.class);
        toDoItem.setDescription(description);
        toDoItem.setCategory(category);
        toDoItem.setOwnedBy(ownedBy);
        persist(toDoItem);
        return toDoItem;
    }
    // }}

    // {{ similarTo (action)
    //@NotInServiceMenu
    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "3")
    public List<ToDoItem> similarTo(final ToDoItem toDoItem) {
        return allMatches(ToDoItem.class, new Filter<ToDoItem>() {
            @Override
            public boolean accept(ToDoItem t) {
                return t != toDoItem && Objects.equal(toDoItem.getCategory(), t.getCategory()) && Objects.equal(toDoItem.getOwnedBy(), t.getOwnedBy());
            }
        });
    }
    // }}
    
    // {{ autoComplete (hidden)
    @Hidden
    @MemberOrder(sequence = "1")
    public List<ToDoItem> autoComplete(final String description) {
        return allMatches(ToDoItem.class, new Filter<ToDoItem>() {
            @Override
            public boolean accept(final ToDoItem t) {
                return ownedByCurrentUser(t) && t.getDescription().contains(description);
            }

        });
    }
    // }}

    // {{ helpers
    protected boolean ownedByCurrentUser(final ToDoItem t) {
        return Objects.equal(t.getOwnedBy(), currentUserName());
    }
    protected String currentUserName() {
        return getContainer().getUser().getName();
    }
    // }}

}
