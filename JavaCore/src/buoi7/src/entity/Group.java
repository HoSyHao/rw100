package buoi7.src.entity;

import java.time.LocalDateTime;

public class Group {
    int groupID;
    String groupName;
    Account creator;
    LocalDateTime createDate;
    Account[] accounts;

//    Question 3:
//    Tạo constructor cho Group:
//    a)	không có parameters
    public Group() {};
//    b)	Có các parameter là GroupName, Creator, array Account[] accounts, CreateDate
    public Group(int groupID, String groupName, Account creator, LocalDateTime createDate, Account[] accounts) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.creator = creator;
        this.createDate = createDate != null ? createDate : LocalDateTime.now();
        this.accounts = accounts;
    };
//    c)	Có các parameter là GroupName, Creator, array String[] usernames , CreateDate
//    Với mỗi username thì sẽ khởi tạo 1 Account (chỉ có thông tin username, các thông tin còn lại = null). Khởi tạo 1 Object với mỗi constructor ở trên
    public Group(int groupID, String groupName, Account creator, LocalDateTime createDate, String[] usernames) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.creator = creator;
        this.createDate = createDate != null ? createDate : LocalDateTime.now();
        this.accounts = new Account[usernames.length];

        for (int i = 0; i < usernames.length; i++) {
            Account account = new Account();
            account.setUsername(usernames[i]);
            this.accounts[i] = account;
        }
    };

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public void setAccounts(Account[] accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        StringBuilder accountNames = new StringBuilder();

        if (accounts != null) {
            for (int i = 0; i < accounts.length; i++) {
                accountNames.append(accounts[i].getUsername());
                if (i < accounts.length - 1) {
                    accountNames.append(", ");
                }
            }
        }

        return "Group{" +
                "groupID=" + groupID +
                ", groupName='" + groupName + '\'' +
                ", creator=" + creator.getFullName() +
                ", createDate=" + createDate +
                ", accounts=[" + accountNames + "]" +
                '}';
    }


}
