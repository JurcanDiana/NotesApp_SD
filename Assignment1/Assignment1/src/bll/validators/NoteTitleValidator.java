package bll.validators;

import model.Note;

public class NoteTitleValidator implements Validator<Note> {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 25;

    @Override
    public boolean validate(Note note) {

        if (note.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Note title cannot be empty");
        }

        if (note.getTitle().length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Note title must be at least " + MIN_LENGTH + " characters long");
        }

        if (note.getTitle().length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Note title must be at most " + MAX_LENGTH + " characters long");
        }

        return true;
    }
}

