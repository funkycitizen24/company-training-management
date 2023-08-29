package com.nttdata.companytrainingmanagement.services;

import com.nttdata.companytrainingmanagement.entities.Author;
import com.nttdata.companytrainingmanagement.repos.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAuthors() {
        // Arrange
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1L, "Joseph", "White"));
        authors.add(new Author(2L, "Nick", "Connor"));

        when(authorRepository.findAll()).thenReturn(authors);

        // Act
        List<Author> result = authorService.getAllAuthors();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Joseph", result.get(0).getFirstName());
        assertEquals("Nick", result.get(1).getFirstName());
    }

    @Test
    public void testGetAuthorById() {
        // Arrange
        Author author = new Author(1L, "Joseph", "White");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        Author foundAuthor = authorService.getAuthorById(1L);
        Author notFoundAuthor = authorService.getAuthorById(2L);

        // Assert
        assertEquals("Joseph", foundAuthor.getFirstName());
        assertNull(notFoundAuthor);
    }

    @Test
    public void testCreateAuthor() {
        // Arrange
        Author newAuthor = new Author(null, "Emily", "Blanca");
        Author savedAuthor = new Author(1L, "Emily", "Blanca");

        when(authorRepository.save(newAuthor)).thenReturn(savedAuthor);

        // Act
        Author createdAuthor = authorService.createAuthor(newAuthor);

        // Assert
        assertEquals(1L, createdAuthor.getId());
        assertEquals("Emily", createdAuthor.getFirstName());
        assertEquals("Blanca", createdAuthor.getLastName());
    }

    @Test
    public void testUpdateAuthor() {
        // Arrange
        Author existingAuthor = new Author(1L, "Joseph", "White");
        Author updatedAuthor = new Author(1L, "Nick", "Connor");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(existingAuthor)).thenReturn(updatedAuthor);

        // Act
        Author result = authorService.updateAuthor(1L, updatedAuthor);

        // Assert
        assertEquals("Nick", result.getFirstName());
        assertEquals("Connor", result.getLastName());
    }

    @Test
    public void testDeleteAuthor() {

        // Act
        authorService.deleteAuthor(1L);

        // Assert
        verify(authorRepository, times(1)).deleteById(1L);
    }
}
