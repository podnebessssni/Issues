package ru.netology.manager;

import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

public class IssueManager {
    private IssueRepository repository;

    public IssueManager(IssueRepository repository){
        this.repository = repository;
    }

    public void add(Issue issue) {
        repository.save(issue);
    }

    public List<Issue> filterByAuthor(Predicate<Issue> predicate) {
        List<Issue> result = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (predicate.test(issue)) {
                result.add(issue);
            }
        }
        return sortByNewest(result);
    }

    public List<Issue> filterByLabel(HashSet<String> label) {
        List<Issue> result = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (issue.getLabel() == label) {
                result.add(issue);
            }
        }
        return result;
    }
    public List<Issue> filterByAssignee(HashSet<String> assignee) {
        List<Issue> result = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (issue.getAssignee() == assignee) {
                result.add(issue);
            }
        }
        return result;
    }

    public List<Issue> sortByNewest(List<Issue> list){
        list.sort((o1,o2) -> o2.getDate().compareTo(o1.getDate()));
        return list;
    }

    public List<Issue> findAllOpen() {
        List<Issue> temp = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (issue.isOpen()) {
                temp.add(issue);

            }
        }
        return temp;
    }
    public List<Issue> findAllClosed() {
        List<Issue> temp = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (!issue.isOpen()) {
                temp.add(issue);

            }
        }
        return temp;
    }

    public void openById(int id) {
        for (Issue issue : repository.getAll()) {
            if (issue.getId() == id) {
                issue.setOpen(true);
                break;
            }
        }
    }
    public void closeById(int id){
        for (Issue issue : repository.getAll()) {
            if (issue.getId() == id) {
                issue.setOpen(false);
                break;
            }
        }
    }

}
