package ua.kiev.prog.Entity;

import javax.persistence.*;

@Entity
@Table(name="client")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;


    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="account_id")
    private Account account;



    @Column(name="name", nullable = false)
    private String name;


    @Column(name="phone", nullable = false)
    private String phone;

    public Client() {}

    public Client(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


}
