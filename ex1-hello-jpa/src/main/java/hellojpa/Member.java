package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name = "USER") //DB에 매핑될 테이블 이름
public class Member {

    @Id //PK가 뭔지 알려줘야 함
    private Long id;
    //@Column(name = "username") //DB에 매핑될 칼럼 이름
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
