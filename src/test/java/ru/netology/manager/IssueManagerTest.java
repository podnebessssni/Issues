package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

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

    private Issue issue1 = new Issue(1, true, "cookie", label1, "10.02.2020", assignee1, 10);
    private Issue issue2 = new Issue(2, false, "smith", label2, "05.05.2020", assignee2, 0);
    private Issue issue3 = new Issue(3, true, "charlie", label3, "15.04.2020", assignee3, 3);
    private Issue issue4 = new Issue(4, true, "charlie", label4, "19.07.2020", assignee1, 0);

    @BeforeEach
    void setUp() {
        manager.add(issue1);
        manager.add(issue2);
        manager.add(issue3);
        manager.add(issue4);
    }

    @Test
    void shouldFilterByAuthor() {
        String author = "charlie";
        Predicate<Issue> predicate = issue -> issue.getAuthor().equals(author);
        List<Issue> actual = manager.filterByAuthor(predicate);
        List<Issue> expected = Arrays.asList(issue4, issue3);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFilterByAuthorIfNotExist() {
        String author = "tommy";
        Predicate<Issue> predicate = issue -> issue.getAuthor().equals(author);
        List<Issue> actual = manager.filterByAuthor(predicate);
        List<Issue> expected = Arrays.asList();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFilterByLabel() {
        Predicate<HashSet> equalLabel = t -> t.equals(label4);
        List<Issue> actual = manager.filterByLabel(equalLabel);
        List<Issue> expected = Arrays.asList(issue4);
        assertEquals(expected, actual);
        System.out.println(issue4.getLabel());
    }

    @Test
    void shouldNotFilterByLableIfNotExist() {
        HashSet<String> label5 = new HashSet<>((Arrays.asList("nottask")));
        Predicate<HashSet> equalLabel = t -> t.equals(label5);
        List<Issue> actual = manager.filterByLabel(equalLabel);
        List<Issue> expected = Arrays.asList();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFilterByAssignee() {
        Predicate<HashSet> equalAssignee = t -> t.equals(assignee1);
        List<Issue> actual = manager.filterByAssignee(equalAssignee);
        List<Issue> expected = Arrays.asList(issue1, issue4);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFilterByAssigneeIfNotExist() {
        Predicate<HashSet> equalAssignee = t -> t.equals(assignee4);
        List<Issue> actual = manager.filterByAssignee(equalAssignee);
        List<Issue> expected = Arrays.asList();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllOpen() {
        List<Issue> actual = manager.findAllOpen();
        List<Issue> expected = Arrays.asList(issue1, issue3, issue4);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllClosed() {
        List<Issue> actual = manager.findAllClosed();
        List<Issue> expected = Arrays.asList(issue2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldOpenById() {

        manager.openById(2);
        assertTrue(issue2.isOpen());
    }

    @Test
    void shouldCloseById() {

        manager.closeById(2);
        assertFalse(issue2.isOpen());
    }


}