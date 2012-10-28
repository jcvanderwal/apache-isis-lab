    // {{ markAsDone (action)
    @MemberOrder(sequence = "1")
    public ToDoItem markAsDone() {
        setDone(true);
        return this;
    }
    // }}

    // {{ markAsNotDone (action)
    @MemberOrder(sequence = "2")
    public ToDoItem markAsNotDone() {
        setDone(false);
        return this;
    }
    // }}
