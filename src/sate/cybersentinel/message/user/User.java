/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sate.cybersentinel.message.user;

import java.io.Serializable;

/**
 *
 * @author tudoreanudb
 */
public interface User extends Serializable, Comparable<User> {
	public String getUUID();
	public String getName();
}
