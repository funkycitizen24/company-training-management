package com.nttdata.companytrainingmanagement.services;

import com.nttdata.companytrainingmanagement.entities.Author;
import com.nttdata.companytrainingmanagement.repos.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    /**
     * Retrieves a list of all authors existing in the DB.
     * @return List of authors.
     */
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Finds an author by their ID.
     * @param id The ID of the author to find.
     * @return The author object with the specified ID, or null if not found.
     */
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    /**
     * Creates a new author in the DB
     * @param author to be created
     * @return the newly created author
     */
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }


    /**
     * Updates an author's firstName and/or lastname
     * @param id The ID of the author to update
     * @param updatedAuthor the Author object with updated information
     * @return The updated author object if the author with the specified ID
     * or null if author is not found
     */
    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author existingAuthor = authorRepository.findById(id).orElse(null);
        if (existingAuthor != null) {
            existingAuthor.setFirstName(updatedAuthor.getFirstName());
            existingAuthor.setLastName(updatedAuthor.getLastName());
            return authorRepository.save(existingAuthor);
        }
        return null;
    }

    /**
     * Lists all authors that have trainings created by them
     * @return the Author list
     */
    public List<Author> getAuthorsWithTrainings() {
        return authorRepository.findAuthorsWithTrainings();
    }

    /**
     * Deletes an author from the DB
     * @param id the unique ID of the author to delete
     */
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
