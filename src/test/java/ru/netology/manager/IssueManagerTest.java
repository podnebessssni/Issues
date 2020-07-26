package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IssueManagerTest {

    private IssueRepository repository = new IssueRepository();
    private IssueManager manager = new IssueManager(repository);

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
    void shouldFilterByAuthor(){
       List<Issue> actual = manager.filterBy(issue -> issue.getAuthor().equals("charlie"));
       List<Issue> expected = Arrays.asList(issue4,issue3);
       assertEquals(expected, actual);
    }
    @Test
    void shouldNotFilterByAuthorIfNotExist(){
       List<Issue> actual = manager.filterBy(issue -> issue.getAuthor().equals("tommy"));
       List<Issue> expected = Arrays.asList();
       assertEquals(expected, actual);
    }

     @Test
    void shouldFilterByLable(){
       List<Issue> actual = manager.filterBy(issue -> issue.getLabel().equals(label4));
       List<Issue> expected = Arrays.asList(issue4);
       assertEquals(expected, actual);
    }
    @Test
    void shouldNotFilterByLableIfNotExist(){
       List<Issue> actual = manager.filterBy(issue -> issue.getLabel().equals(new HashSet<>()));
       List<Issue> expected = Arrays.asList();
       assertEquals(expected, actual);
    }
    @Test
    void shouldFilterByAssignee(){
       List<Issue> actual = manager.filterBy(issue -> issue.getAssignee().equals(assignee1));
       List<Issue> expected = Arrays.asList(issue4,issue1);
       assertEquals(expected, actual);
    }
    @Test
    void shouldNotFilterByAssigneeIfNotExist(){
       List<Issue> actual = manager.filterBy(issue -> issue.getAssignee().equals(new HashSet<>()));
       List<Issue> expected = Arrays.asList();
       assertEquals(expected, actual);
    }
    @Test
    void shouldFindAllOpen(){
       List<Issue> actual = manager.findAllOpen();
       List<Issue> expected = Arrays.asList(issue1, issue3, issue4);
       assertEquals(expected, actual);
    }
    @Test
    void shouldFindAllClosed(){
       List<Issue> actual = manager.findAllClosed();
       List<Issue> expected = Arrays.asList(issue2);
       assertEquals(expected, actual);
    }


}