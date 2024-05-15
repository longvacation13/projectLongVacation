package com.common;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.type.StringType;

import java.sql.Types;

/**
  https://deep-jin.tistory.com/entry/Spring-Boot%EC%99%80-sqlite3-%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0-Hibernate
  https://github.com/C-YooJin/lite/blob/main/src/main/java/com/deepjin/lite/SQLDialect.java
  JPA로 어플리케이션을 개발하게 되면 개발자가 직접 JDBC 레벨에서 SQL문을 작성하는게 아니고 JPA가 이를 대신해주게 되는데 그 때 필요한게 Dialect다.
  SQLDialect는 데이터베이스간의 SQL문법 차이를 보정해주기 위해 JPA가 제공하는 클래스다.
  나는 Dialect 클래스를 상속받은 SQLDialect 클래스를 생성해서 해당 클래스를 properties 파일에 경로로 주었다.
  참고로 JPA와 Hibernate를 확실히 구분짓고 넘어가는게 좋을 것 같다.
  JPA란 그냥 어떤 개념이라고 보면 된다.
  백엔드에서 디비를 다룰 때 어떤 방식으로 디비를 다루겠다는 명세정도다.
  JPA를 실제 라이브러리로 구현한 구현체가 Hibernate다.
  따라서 SQLDialect는 Hibernate를 활용해서 설정한다고 보면 된다.
 */

public class SQLDialect extends Dialect {

    /**
     * registerColumnType : JDBC 타입을 SQLite 데이터베이스의 적절한 타입으로 매핑
     * registerFunction : 특정 SQL 함수를 정의해서 SQLite에서 사용할 수 있도록 등록함
     */
    public SQLDialect() {
        registerColumnType(Types.BIT, "integer");
        registerColumnType(Types.TINYINT, "tinyint");
        registerColumnType(Types.SMALLINT, "smallint");
        registerColumnType(Types.INTEGER, "integer");
        registerColumnType(Types.BIGINT, "bigint");
        registerColumnType(Types.FLOAT, "float");
        registerColumnType(Types.REAL, "real");
        registerColumnType(Types.DOUBLE, "double");
        registerColumnType(Types.NUMERIC, "numeric");
        registerColumnType(Types.DECIMAL, "decimal");
        registerColumnType(Types.CHAR, "char");
        registerColumnType(Types.VARCHAR, "varchar");
        registerColumnType(Types.LONGVARCHAR, "longvarchar");
        registerColumnType(Types.DATE, "date");
        registerColumnType(Types.TIME, "time");
        registerColumnType(Types.TIMESTAMP, "timestamp");
        registerColumnType(Types.BINARY, "blob");
        registerColumnType(Types.VARBINARY, "blob");
        registerColumnType(Types.LONGVARBINARY, "blob");
        registerColumnType(Types.BLOB, "blob");
        registerColumnType(Types.CLOB, "clob");
        registerColumnType(Types.BOOLEAN, "integer");

        registerFunction("concat", new VarArgsSQLFunction(StringType.INSTANCE, "", "||", ""));
        registerFunction("mod", new SQLFunctionTemplate(StringType.INSTANCE, "?1 % ?2"));
        registerFunction("substr", new StandardSQLFunction("substr", StringType.INSTANCE));
        registerFunction("substring", new StandardSQLFunction("substr", StringType.INSTANCE));

    }

    /**
     * SQLite가 IDENTITY 컬럼 (자동증가 컬럼)을 지원하는지 여부를 반환함
     * @return true
     */
    public boolean supportsIdentityColumns() {
        return true;
    }

    /**
     * IDENTITY 컬럼에 데이터 타입을 명시해야하는지 여부를 반환함.
     * SQLite 에서는 필요없기 때문에 false 를 반환함
     * @return
     */
    public boolean hasDataTypeInIdentityColumn() {
        return false; // As specify in NHibernate dialect
    }

    /**
     * IDENTITY 컬럼을 정의할 때 사용하는 SQL 구문을 반환함
     * @return
     */
    public String getIdentityColumnString() {
        // return "integer primary key autoincrement";
        return "integer";
    }

    /**
     * 마지막으로 삽입된 IDENTITY 값을 조회하는 SQL 구문을 반환함
     * @return
     */
    public String getIdentitySelectString() {
        return "select last_insert_rowid()";
    }


    /**
     * SQLite가 LIMIT 절을 지원하는지 여부를 반환함
     * @return
     */
    public boolean supportsLimit() {
        return true;
    }

    /**
     * 주어진 쿼리에 Limit 절을 추가하여 반환함
     * OFFSET이 있는 경우와 없는 경우를 처리함 - OFFSET이란?
     * @param query The query to which to apply the limit.
     * @param hasOffset Is the query requesting an offset?
     * @return
     */
    protected String getLimitString(String query, boolean hasOffset) {
        return new StringBuffer(query.length() + 20).append(query).append(hasOffset ? " limit ? offset ?" : " limit ?")
                .toString();
    }

    /**
     * SQLite가 임시테이블을 지원하는지 여부를 반환함
     * @return
     */
    public boolean supportsTemporaryTables() {
        return true;
    }

    /**
     * 임시 테이블을 생성하는 SQL 구문을 반환함
     * @return
     */
    public String getCreateTemporaryTableString() {
        return "create temporary table if not exists";
    }

    /**
     * 임시 테이블 사용 후 삭제할 지 여부를 반환함
     * @return
     */
    public boolean dropTemporaryTableAfterUse() {
        return false;
    }

    /**
     * SQLite가 현재 타임스탬프를 선택하는 기능을 지원하는지 여부를 반환함
     * @return
     */
    public boolean supportsCurrentTimestampSelection() {
        return true;
    }

    /**
     * 현재 타임스탬프를 선택하는 SQL구문이 함수호출 형태인지 여부를 반환함
     * @return
     */
    public boolean isCurrentTimestampSelectStringCallable() {
        return false;
    }

    /**
     * 현재 타임스탬프를 선택하는 SQL 구문을 반환함
     * @return
     */
    public String getCurrentTimestampSelectString() {
        return "select current_timestamp";
    }

    /**
     * SQLite가 UNION ALL을 지원하는지 여부를 반환함
     * @return
     */
    public boolean supportsUnionAll() {
        return true;
    }


    /**
     * SQLite가 ALTER TABLE 구문을 지원하는지 여부를 반환함
     * @return
     */
    public boolean hasAlterTable() {
        return false; // As specify in NHibernate dialect
    }

    /**
     * SQLite가 제약조건을 삭제하는 기능을 지원하는지 여부를 반환함
     * @return
     */
    public boolean dropConstraints() {
        return false;
    }

    /**
     * 테이블에 컬럼을 추가하는 SQL 구문을 반환함
     * @return
     */
    public String getAddColumnString() {
        return "add column";
    }

    /**
     * FOR UPDATE 구문을 반환함
     * SQLite에서는 지원하지 않기 때문에 빈 문자열을 반환함
     * @return
     */
    public String getForUpdateString() {
        return "";
    }

    /**
     * SQLite가 OUTER JOIN 과 FOR UPDATE를 함께 사용할 수 있는지 여부를 반환함
     * @return
     */
    public boolean supportsOuterJoinForUpdate() {
        return false;
    }

    /**
     * 외래 키를 삭제하는 SQL 구문을 반환함.
     * SQLite에서는 지원하지 않으므로 예외를 발생시킴
     * @return
     */
    public String getDropForeignKeyString() {
        throw new UnsupportedOperationException("No drop foreign key syntax supported by SQLiteDialect");
    }

    /**
     * 외래키 제약조건을 추가하는 SQL 구문을 반환함.
     * SQLite에서는 지원하지 않으므로 예외를 발생시킴
     * @param constraintName The FK constraint name.
     * @param foreignKey The names of the columns comprising the FK
     * @param referencedTable The table referenced by the FK
     * @param primaryKey The explicit columns in the referencedTable referenced
     * by this FK.
     * @param referencesPrimaryKey if false, constraint should be
     * explicit about which column names the constraint refers to
     *
     * @return
     */
    public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable,
                                                   String[] primaryKey, boolean referencesPrimaryKey) {
        throw new UnsupportedOperationException("No add foreign key syntax supported by SQLiteDialect");
    }

    /**
     * 기본키 제약조건을 추가하는 SQL 구문을 반환함
     * SQLite에서는 지원하지 않으므로 예외를 발생시킴
     * @param constraintName The name of the PK constraint.
     * @return
     */
    public String getAddPrimaryKeyConstraintString(String constraintName) {
        throw new UnsupportedOperationException("No add primary key syntax supported by SQLiteDialect");
    }

    /**
     * 테이블 이름 앞에 IF EXISTS 를 사용할 수 있는지 여부를 반환함
     * @return
     */
    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    /**
     * SQLite가 삭제 연산시 연쇄 삭제를 지원하는지 여부를 반환함*
     * @return
     */
    public boolean supportsCascadeDelete() {
        return false;
    }
}
