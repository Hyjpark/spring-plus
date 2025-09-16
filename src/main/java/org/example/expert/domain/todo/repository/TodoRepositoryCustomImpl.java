package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;

@RequiredArgsConstructor
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(todo)
                .where(todo.id.eq(todoId))
                .leftJoin(todo.user).fetchJoin()
                .fetchOne());
    }

    @Override
    public Page<TodoSearchResponse> searchTodos(Pageable pageable, String title, String nickname, LocalDate startDate, LocalDate endDate) {

        boolean hasSearchCondition = (title != null && !title.isBlank())
                || (nickname != null && !nickname.isBlank())
                || startDate != null
                || endDate != null;

        if (!hasSearchCondition) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<TodoSearchResponse> results = jpaQueryFactory.select(
                        Projections.constructor(TodoSearchResponse.class,
                                todo.title,
                                manager.id.countDistinct().intValue(),
                                comment.id.countDistinct().intValue()
                        ))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .where(titleLike(title), nicknameLike(nickname), betweenDate(startDate, endDate))
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(todo.count())
                .from(todo)
                .where(titleLike(title), nicknameLike(nickname), betweenDate(startDate, endDate));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    private BooleanExpression titleLike(String title) {
        return title != null ? todo.title.contains(title) : null;
    }

    private BooleanExpression nicknameLike(String nickname) {
        return nickname != null ? todo.user.nickname.contains(nickname) : null;
    }

    private BooleanExpression betweenDate(LocalDate startDate, LocalDate endDate) {
        BooleanExpression condition = null;

        if (startDate != null) {
            LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
            condition = todo.createdAt.goe(startDateTime);
        }

        if (endDate != null) {
            LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX).withNano(0);
            BooleanExpression endCondition = todo.createdAt.loe(endDateTime);

            condition = (condition != null) ? condition.and(endCondition) : endCondition;
        }

        return condition;
    }
}
