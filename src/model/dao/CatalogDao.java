package model.dao;

import model.entities.Catalog;
import view.View;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Artem Pryzhkov
 * Class that contains "instruction" methods that show which special functions 
 * to work with Catalog  must have inheritance class.
 * Implements class AbstractDao that contains general "instruction" methods for all entities
 *
 */
public interface CatalogDao extends AbstractDao<Catalog> {
}
