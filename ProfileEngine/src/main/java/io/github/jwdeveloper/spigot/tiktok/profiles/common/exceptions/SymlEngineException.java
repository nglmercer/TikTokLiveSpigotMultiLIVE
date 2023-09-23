package io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions;

public class SymlEngineException extends RuntimeException
{
    public SymlEngineException()
    {
    }

    public SymlEngineException(String message)
    {
        super(message);
    }

    public SymlEngineException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SymlEngineException(Throwable cause)
    {
        super(cause);
    }

    public SymlEngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
