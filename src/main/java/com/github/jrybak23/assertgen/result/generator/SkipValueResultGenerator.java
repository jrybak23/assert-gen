package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.call.experession.CallExpression;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Executable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Lock;
import java.util.stream.BaseStream;

class SkipValueResultGenerator implements ResultGenerator {

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof Date
                || value instanceof Calendar
                || value instanceof ZoneId
                || value instanceof TemporalAmount
                || value instanceof TimeZone
                || value instanceof Currency
                || value instanceof UUID
                || value instanceof Locale
                || value instanceof File
                || value instanceof Path
                || value instanceof Process
                || value instanceof Lock
                || value instanceof AtomicStampedReference<?>
                || value instanceof AtomicReference<?>
                || value instanceof AtomicInteger
                || value instanceof AtomicLong
                || value instanceof AtomicIntegerArray
                || value instanceof AtomicLongArray
                || value instanceof AtomicBoolean
                || value instanceof Future<?>
                || value instanceof Type
                || value instanceof Executable
                || value instanceof BaseStream
                || value instanceof Iterator<?>
                || value instanceof Charset
                || (value instanceof Dictionary<?, ?> && !(value instanceof Map<?, ?>))
                || value instanceof BigDecimal
                || value instanceof BigInteger
                || value instanceof InputStream
                || value instanceof OutputStream
                || value instanceof Reader
                || value instanceof Writer
                || value instanceof Buffer
                || value instanceof Throwable;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, CallExpression callExpression, Object value) {
        Class<?> aClass = value.getClass();
        System.err.println("[WARN] Object of type " + aClass.getName() + " is skipped because it's not supported yet.");
        codeAppender.appendNewLine("assertThat(" + callExpression + " instanceof " + aClass.getSimpleName() + ").isTrue();");
    }
}
