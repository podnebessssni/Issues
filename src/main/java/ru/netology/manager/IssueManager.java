package ru.netology.manager;

import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.*;
import java.util.function.Predicate;

public class IssueManager {
    private IssueRepository repository;

    public IssueManager(IssueRepository repository){
        this.repository = repository;
    }

    public List<Issue> filterBy(Predicate<Issue> predicate) {
        List<Issue> result = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (predicate.test(issue)) {
                result.add(issue);
            }
        }
        return sortByNewest(result);
    }

    public List<Issue> sortByNewest(List<Issue> list){
        list.sort(Comparator.comparing(Issue::getDate).reversed());
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

}
