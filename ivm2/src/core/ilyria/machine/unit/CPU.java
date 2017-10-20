/**
 * بسم الله الرحمن الرحيم
 * رخصة إليريا التعليمية العامة
 *
 * يمكنك استخدامها كيفما شئت
 * لكن لأغراض تعليمية فقط
 * مع مراعات مشاركتك لما أضفته مع الآخرين بنفس الرخصة الحالية
 *
 * كتبها إلياس سعداوي
 * في 23/8/1438
 * الموافق لـ 19/2/2017
 *
 * في أمان الله
 *
 */
package core.ilyria.machine.unit;

import core.ilyria.machine.IlyriaMachine;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author ilyria
 */
public class CPU implements IlyriaMachine
{
    /** 8 mega byte of memory */
    public final static int MEMORY_SIZE = 8388608;
    
    /** the next CPU id */
    private static int next_Id = 0;
    
    /** CPU id */
    public final int cpuId;
    
    protected final FetchDecodeUnit fetchDecode;
    protected final ExecutionUnit execution;
    
    /** CPU 8 mega byte Random Access Memory */
    protected final ByteBuffer ram;
    
    /** Input Unit */
    protected final Scanner input;
    
    /** Output Unit */
    protected final PrintStream output;
        
    public CPU()
    {
        // init cpu id
        cpuId = next_Id++;
        
        // init the Random Access Memory
        ram = ByteBuffer.allocate( MEMORY_SIZE );
        
        // init Input Unit
        input = new Scanner( System.in );
        
        // init Output Unit
        output = System.out;
        
        // init Execution Unit
        execution = new ExecutionUnit( ram, input, output );
        
        // init Fectch and Decode Unit
        fetchDecode = new FetchDecodeUnit(execution, ram );
        
    } // end one-argument CPU constructor
    
    @Override
    public int fetch()
    {
        return fetchDecode.fetch();
    } // end method fetch
    
    @Override
    public int[] decode()
    {
        return fetchDecode.decode();
    } // end method decode
    
    @Override
    public int execute()
    {
        return execution.execute();
    } // end method execute

    @Override
    public void reset()
    {
        // set every byte on Random Access Memory to zero
        for ( int i = 0; i < ram.capacity(); ++i )
            ram.put( i, (byte)0 );
        
        // reset the ExecutionUnit
        execution.reset();
    } // end method reset
    
    //--------------------------begin get only----------------------------------
    
    public ByteBuffer getRam()
    {
        return ram;
    } // end method getRam
    
    public FetchDecodeUnit getFetchDecodeUnit()
    {
        return fetchDecode;
    } // end method getFetchDecodeUnit
    
    public ExecutionUnit getExecutionUnit()
    {
        return execution;
    } // end method getExecutionUnit

    public Scanner getInput()
    {
        return input;
    } // end method getInput

    public PrintStream getOutput()
    {
        return output;
    } // end method getOutput
    
} // end class CPU
