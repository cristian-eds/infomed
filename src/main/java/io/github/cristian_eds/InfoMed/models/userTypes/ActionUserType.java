package io.github.cristian_eds.InfoMed.models.userTypes;

import io.github.cristian_eds.InfoMed.models.enums.ActionType;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ActionUserType implements UserType<ActionType> {

    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

    @Override
    public Class<ActionType> returnedClass() {
        return ActionType.class;
    }

    @Override
    public boolean equals(ActionType x, ActionType y) {
        return x != null && x.equals(y);
    }

    @Override
    public int hashCode(ActionType x) {
        return x.hashCode();
    }

    @Override
    public ActionType nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String actionType = rs.getString(position);
        if(actionType == null) return null;
        return ActionType.valueOf(actionType);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, ActionType value, int index, SharedSessionContractImplementor session) throws SQLException {
        if(value == null) {
            st.setNull(index,Types.VARCHAR);
        } else {
            st.setString(index, value.toString());
        }
    }

    @Override
    public ActionType deepCopy(ActionType value) {
        if(value == null) return null;
        return ActionType.valueOf(value.toString());
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(ActionType value) {
        return (Serializable) deepCopy(value);
    }

    @Override
    public ActionType assemble(Serializable cached, Object owner) {
        return deepCopy( (ActionType) cached);
    }
}
