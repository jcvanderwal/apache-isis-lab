    // {{ Dependencies (Collection)
    private List<ToDoItem> dependencies = new ArrayList<ToDoItem>();
    @Disabled
    @MemberOrder(sequence = "1")
    public List<ToDoItem> getDependencies() {
        return dependencies;
    }
    public void setDependencies(final List<ToDoItem> dependencies) {
        this.dependencies = dependencies;
    }
    // }}
