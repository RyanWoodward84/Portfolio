class UniqueEmailAddresses {
    public int numUniqueEmails(String[] emails) {
        //We use a set and HashSet to contain the list of unique email
        //addresses. It's possible to use linked list, but this implementation
        //is black box. Also hashset will not consider ordering which we don't
        //need and considers time performance
        Set<String> uniqueEmails = new HashSet();
        for (String email : emails) {
            int i = email.indexOf('@');
            String local = email.substring(0, i);
            String domain = email.substring(i);
            if (local.contains("+")) {
                local = local.substring(0, local.indexOf('+'));
            }
            //now we need to get rid of the '.' character
            local = local.replaceAll("\\.", "");
            //domain = domain.replaceAll(".", "");
            uniqueEmails.add(local + domain);
            
        }
        return uniqueEmails.size();  
    }
}