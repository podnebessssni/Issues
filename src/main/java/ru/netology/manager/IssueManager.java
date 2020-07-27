package ru.netology.manager;

import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.ArrayList;
import java.util.HashSet;
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

    public List<Issue> filterByLabel(Predicate<HashSet> equalLabel) {
        List<Issue> result = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (equalLabel.test(issue.getLabel())) {
                result.add(issue);
            }
        }
        return result;
    }

    public List<Issue> filterByAssignee(Predicate<HashSet> equalAssignee) {
        List<Issue> result = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (equalAssignee.test(issue.getAssignee())) {
                result.add(issue);
            }
        }
        return result;
    }

    public List<Issue> sortByNewest(List<Issue> list) {
        list.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
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
        repository.openById(id);
    }

    public void closeById(int id){
        repository.closeById(id);
    }
}
