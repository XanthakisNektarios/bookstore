ALTER TABLE BOOK
    MODIFY COLUMN AUTHOR_ID BIGINT NOT NULL,
    ADD FOREIGN KEY (AUTHOR_ID)
    REFERENCES AUTHOR(AUTHOR_ID);
