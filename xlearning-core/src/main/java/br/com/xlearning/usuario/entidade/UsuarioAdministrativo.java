package br.com.xlearning.usuario.entidade;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "admin")
@NamedQueries({
    @NamedQuery(name = "UsuarioAdministrativo.findAll", query = "SELECT adm FROM UsuarioAdministrativo adm"),
    @NamedQuery(name = "UsuarioAdministrativo.findByMatricula", query = "SELECT adm FROM UsuarioAdministrativo adm WHERE adm.matricula = :matricula")})
public class UsuarioAdministrativo extends Usuario
{
   private static final long serialVersionUID = 1L;
   

}
