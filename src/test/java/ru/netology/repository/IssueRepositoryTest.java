package ru.netology.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IssueRepositoryTest {

    private IssueRepository repository = new IssueRepository();

    private HashSet<String> assignee1 = new HashSet<>((Arrays.asList("jackie")));
    private HashSet<String> assignee2 = new HashSet<>((Arrays.asList("sbrannen")));
    private HashSet<String> assignee3 = new HashSet<>((Arrays.asList("whitie")));
    private HashSet<String> assignee4 = new HashSet<>((Arrays.asList("dennis")));

    private HashSet<String> label1 = new HashSet<>((Arrays.asList("BUG")));
    private HashSet<String> label2 = new HashSet<>((Arrays.asList("new feature")));
    private HashSet<String> label3 = new HashSet<>((Arrays.asList("task")));
    private HashSet<String> label4 = new HashSet<>((Arrays.asList("question")));

    private Issue issue1 = new Issue(1,true,"cookie", label1, "10.02.2020", assignee1, 10);
    private Issue issue2 = new Issue(2,false,"smith", label2, "05.05.2020", assignee2, 0);
    private Issue issue3 = new Issue(3,true,"charlie", label3, "15.04.2020", assignee3, 3);
    private Issue issue4 = new Issue(4,true,"charlie", label4, "19.07.2020", assignee1, 0);

    @BeforeEach
    void setUp(){
        repository.save(issue1);
        repository.save(issue2);
        repository.save(issue3);
        repository.save(issue4);
    }

    @Test
    void shouldFindAll(){
    List<Issue> actual = repository.getAll();
    List<Issue> expected = Arrays.asList(issue1,issue2,issue3,issue4);

    assertEquals(expected,actual);
    }
    @Test
    void shouldFindById(){
    Issue actual = repository.findById(3);
    Issue expected = issue3;

    assertEquals(expected,actual);
    }
    @Test
    void shouldOpenById(){

     repository.openById(2);
     assertTrue(issue2.isOpen());
    }
    @Test
    void shouldCloseById(){

     repository.closeById(2);
     assertFalse(issue2.isOpen());
    }

}