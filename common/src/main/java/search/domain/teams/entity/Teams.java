package search.domain.teams.entity;

import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import search.domain.members.entity.Members;

@Entity
@Getter
@Table(name = "teams")
public class Teams {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "teams", fetch = LAZY)
    private List<Members> members = new ArrayList<>();

    public void kickedMember(List<Long> membersId) {
        Iterator<Members> membersIterator = this.members.iterator();
        while (membersIterator.hasNext()){
            Members member = membersIterator.next();
            if(membersId.contains(member.getId())){
                member.removeTeam();
                membersIterator.remove();
            }
        }
    }
}
