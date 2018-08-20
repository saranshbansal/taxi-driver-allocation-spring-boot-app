package com.mytaxi.exception;

public class CarAlreadyInUseException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 959516950338166346L;

    public CarAlreadyInUseException(final String message)
    {
        super(message);
    }

}
