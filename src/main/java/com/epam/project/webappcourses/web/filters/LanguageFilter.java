package com.epam.project.webappcourses.web.filters;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.epam.project.webappcourses.dao.implemented_dao.JDBCLanguageDao;
import com.epam.project.webappcourses.entities.Language;
import com.epam.project.webappcourses.exceptions.DBException;

public class LanguageFilter implements Filter {

    private List<Language> languages = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            languages = new JDBCLanguageDao().findAllLanguages();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException {
        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        final HttpSession session = httpRequest.getSession();
        if (session != null) {
            final Object lang = session.getAttribute("lang");
            if (lang == null)
                session.setAttribute("lang", languages.get(0).getShortName());
        }
        if (session != null && httpRequest.isRequestedSessionIdValid())
            session.setAttribute("languages", languages);
        next.doFilter(request, response);
    }
}
