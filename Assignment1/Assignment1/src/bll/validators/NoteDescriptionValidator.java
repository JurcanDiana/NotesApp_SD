package bll.validators;

import model.Note;

public class NoteDescriptionValidator implements Validator<Note> {

    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 100;

    @Override
    public boolean validate(Note note) {

        String description = note.getDescription();

        if (description.length() < MIN_LENGTH || description.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("The description length is not within the required range!");
        }

        return true;
    }
}

