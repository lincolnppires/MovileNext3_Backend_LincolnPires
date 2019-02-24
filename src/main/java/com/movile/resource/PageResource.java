package com.movile.resource;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PageResource<T> extends ResourceSupport implements Page<T> {

	
	private final Page<T> page;

	
	public PageResource(Page<T> page, String pageParam, String sizeParam) {
		this.page = page;		
		if (page.hasPrevious()) {
			String path = createBuilder().queryParam(pageParam, page.getNumber() - 1)
										 .queryParam(sizeParam, page.getSize()).build().toUriString();
			Link link = new Link(path, Link.REL_PREVIOUS);
			add(link);
		}
		if (page.hasNext()) {
			String path = createBuilder().queryParam(pageParam, (page.getNumber() + 1))
					                     .queryParam(sizeParam, page.getSize()).build().toUriString();
			Link link = new Link(path, Link.REL_NEXT);
			add(link);
		}

		Link link = buildPageLink(pageParam, 0, sizeParam, page.getSize(), Link.REL_FIRST);
		add(link);

		int indexOfLastPage = page.getTotalPages() - 1;
		link = buildPageLink(pageParam, indexOfLastPage, sizeParam, page.getSize(), Link.REL_LAST);
		add(link);

		link = buildPageLink(pageParam, page.getNumber(), sizeParam, page.getSize(), Link.REL_SELF);
		add(link);
	}

	@JsonIgnore
	private ServletUriComponentsBuilder createBuilder() {
		return ServletUriComponentsBuilder.fromCurrentRequestUri();
	}

	@JsonIgnore
	private Link buildPageLink(String pageParam, int page, String sizeParam, int size, String rel) {
		String path = createBuilder().queryParam(pageParam, page).queryParam(sizeParam, size).build().toUriString();
		Link link = new Link(path, rel);
		return link;
	}

	@JsonIgnore
	@Override
	public int getNumber() {
		return page.getNumber();
	}

	@JsonIgnore
	@Override
	public int getSize() {
		return page.getSize();
	}

	@JsonIgnore
	@Override
	public int getTotalPages() {
		return page.getTotalPages();
	}
	@JsonIgnore
	@Override
	public int getNumberOfElements() {
		return page.getNumberOfElements();
	}
	@JsonIgnore
	@Override
	public long getTotalElements() {
		return page.getTotalElements();
	}
	@JsonIgnore
	@Override
	public boolean hasPrevious() {
		return page.hasPrevious();
	}
	@JsonIgnore
	@Override
	public boolean isFirst() {
		return page.isFirst();
	}
	@JsonIgnore
	@Override
	public boolean hasNext() {
		return page.hasNext();
	}
	@JsonIgnore
	@Override
	public boolean isLast() {
		return page.isLast();
	}
	@JsonIgnore
	@Override
	public Iterator<T> iterator() {
		return page.iterator();
	}
	
	@Override
	public List<T> getContent() {
		return page.getContent();
	}
	
	@JsonIgnore
	@Override
	public boolean hasContent() {
		return page.hasContent();
	}
	
	@JsonIgnore
	@Override
	public Sort getSort() {
		return page.getSort();
	}
	
	@JsonIgnore
	@Override
	public Pageable nextPageable() {
		return page.nextPageable();
	}
	
	@JsonIgnore
	@Override
	public Pageable previousPageable() {
		return page.previousPageable();
	}
	
	@JsonIgnore
	@Override
	public <U> Page<U> map(Function<? super T, ? extends U> converter) {
		return null;
	}
}

