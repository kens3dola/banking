package bankingsystem.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Transaction implements Comparable<Transaction> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "transfer_from")
    private String transferFrom;
    @Column(name = "transfer_to")
    private String transferTo;
    @Column(name = "amount")
    private Long amount;
    @Column(name = "date")
    private Date date;
    @Column(name = "type")
    private String type;

    @Override
    public int compareTo(Transaction transaction) {
        return this.getDate().compareTo(transaction.getDate());
    }
}
