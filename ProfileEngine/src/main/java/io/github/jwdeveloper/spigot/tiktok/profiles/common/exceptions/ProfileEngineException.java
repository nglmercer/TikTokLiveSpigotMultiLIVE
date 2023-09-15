package io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions;

public class ProfileEngineException extends RuntimeException
{
    public ProfileEngineException()
    {
    }

    public ProfileEngineException(String message)
    {
        super(message);
    }

    public ProfileEngineException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ProfileEngineException(Throwable cause)
    {
        super(cause);
    }

    public ProfileEngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
