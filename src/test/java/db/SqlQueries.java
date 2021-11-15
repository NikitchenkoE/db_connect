package db;

public interface SqlQueries {
    String CREATE_TABLE_PERSONS = """
            CREATE TABLE Persons (
                PersonID int,
                LastName varchar(255),
                FirstName varchar(255)
            );""";

    String DROP_TABLE_PERSONS = "DROP TABLE Persons";

    String CREATE_TABLE_BEFORE = """
            CREATE TABLE Before (
                PersonID int,
                LastName varchar(255)
            );""";

    String INSERT_INTO_BEFORE_TABLE = """
            INSERT INTO Before\s
            VALUES (1, 'Fedorov'),
            (2, 'Nikitchenko')""";

    String ALTER_TABLE_BEFORE = "ALTER TABLE Before ADD COLUMN alterColumn VARCHAR(255) NOT NULL";

    String RENAME_TABLE_BEFORE = "ALTER TABLE Before RENAME TO new_table_name;";
    String RENAME_TABLE_BACK = "ALTER TABLE new_table_name RENAME TO Before;";

    String DROP_TABLE_BEFORE = "DROP TABLE Before";
}
