package org.cnt.appointmentmanagementtest.common.annotations;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class DefaultPageableArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return parameter.hasParameterAnnotation(DefaultPageable.class)
                && Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        DefaultPageable annotation = parameter.getParameterAnnotation(DefaultPageable.class);
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        boolean hasPage = hasText(request.getParameter("page"));
        boolean hasSize = hasText(request.getParameter("size"));
        boolean hasSortField = hasText(request.getParameter("sortField"));
        boolean hasSortDirection = hasText(request.getParameter("sortDirection"));

        if (!hasPage && !hasSize && !hasSortField && !hasSortDirection) {
            return Pageable.unpaged();
        }

        int page = getIntParameter(request, "page", annotation.page());
        int size = getIntParameter(request, "size", annotation.size());
        String sortField = getStringParameter(request, "sortField", annotation.sort());
        String sortDirection = getStringParameter(request, "sortDirection", annotation.direction());

        if (!hasText(sortField)) {
            return PageRequest.of(page, size);
        }

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        return PageRequest.of(page, size, Sort.by(direction, sortField));
    }

    private int getIntParameter(HttpServletRequest request, String name, int defaultValue) {
        String value = request.getParameter(name);
        return value == null || value.isBlank() ? defaultValue : Integer.parseInt(value);
    }

    private String getStringParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}