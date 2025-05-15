ALTER TABLE quizzes
ALTER COLUMN deadline TYPE timestamp USING deadline::timestamp;