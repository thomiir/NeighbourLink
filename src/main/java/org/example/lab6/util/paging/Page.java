package org.example.lab6.util.paging;

public class Page<E> {
    final Iterable<E> elementsOnPage;

    public Page(Iterable<E> elementsOnPage) {
        this.elementsOnPage = elementsOnPage;
    }

    public Iterable<E> getElementsOnPage() {
        return elementsOnPage;
    }
}
