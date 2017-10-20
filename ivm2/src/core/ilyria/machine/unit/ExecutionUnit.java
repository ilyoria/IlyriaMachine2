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
import java.util.Scanner;

import static core.ilyria.machine.IlyriaMachine.*;
import java.util.HashMap;

/**
 *
 * @author ilyria
 */
public class ExecutionUnit
{
    public static final int SUCCESSFUL_EXECUTION = 0;
    public static final int WRONG_EXECUTION = 1;
    
    protected double accum; 
 
    protected int operationCode;
    protected int operand; 
    
    protected byte calculationType;
    protected byte previousCaltype;
    protected byte precision;
    
    protected int instruction;   
    protected int programCounter;
    
    protected int dataIndex;
    
    protected byte strLength;
    
    private final byte INSTRUCTION_LENGTH;
    
    private final int END_INSTRUCTION;
    private final int END_DATA;
    private final int END_MEMORY;

    private final ByteBuffer ram;
    
    protected final Scanner input;
    protected final PrintStream output;
    
    /** strings */
    protected final HashMap< Integer, String > strings = new HashMap<>();
    
    
    public ExecutionUnit( ByteBuffer ram, Scanner input, PrintStream output )
    {
        this.ram = ram;
        this.input = input;
        this.output = output;
        
        END_INSTRUCTION = ram.capacity() / 3;
        END_DATA        = (ram.capacity() / 3 ) * 2;
        END_MEMORY      = ram.capacity();
        
        INSTRUCTION_LENGTH = 
           (byte)(String.valueOf( ram.capacity() - 1 ).length() );
        
        dataIndex = END_INSTRUCTION;
    } // end three-argument ExecutionUnit constructor
    
    //-----------------------------get end set----------------------------------
    public double getAccum()
    {
        return accum;
    }

    public void setAccum( double accum )
    {
        this.accum = accum;
    }

    public int getOperationCode()
    {
        return operationCode;
    }

    public void setOperationCode( int operationCode )
    {
        this.operationCode = operationCode;
    }

    public int getOperand()
    {
        return operand;
    }

    public void setOperand( int operand )
    {
        this.operand = operand;
    }

    public byte getCalculationType()
    {
        return calculationType;
    }

    public void setCalculationType( byte calculationType )
    {
        this.setPreviousCaltype( getCalculationType() );
        this.calculationType = calculationType;
    }

    public byte getPreviousCaltype()
    {
        return previousCaltype;
    }

    private void setPreviousCaltype( byte previousCaltype )
    {
        this.previousCaltype = previousCaltype;
    }

    public byte getPrecision()
    {
        return precision;
    }

    public void setPrecision( byte precision )
    {
        this.precision = precision;
    }

    public int getInstruction()
    {
        return instruction;
    }

    public void setInstruction( int instruction )
    {
        this.instruction = instruction;
    }

    public int getProgramCounter()
    {
        return programCounter;
    }

    public void setProgramCounter( int programCounter )
    {
        this.programCounter = programCounter;
    }

    public int getDataIndex()
    {
        return dataIndex;
    }

    public void setDataIndex( int dataIndex )
    {
        this.dataIndex = dataIndex;
    }

    public byte getStrLength()
    {
        return strLength;
    }

    public void setStrLength( byte strLength )
    {
        this.strLength = strLength;
    }
    
    //-----------------------get only-------------------------------------------
    public int END_INSTRUCTION()
    {
        return END_INSTRUCTION;
    }

    public int END_DATA()
    {
        return END_DATA;
    }

    public int END_MEMORY()
    {
        return END_MEMORY;
    }
    
    public int INSTRUCTION_LENGTH()
    {
        return INSTRUCTION_LENGTH;
    }
    
    public HashMap< Integer, String> getStrings()
    {
        return strings;
    }
    //-----------------------end get only---------------------------------------

    public void reset()
    {
        setAccum( 0 );
        
        setOperationCode( 0 );
        setOperand( 0 );
        
        setCalculationType( (byte)1 );
        setPrecision( (byte)2 );
        
        setInstruction( 0 );
        setProgramCounter( 0 );
        
        setDataIndex( END_INSTRUCTION );
    } // end method clear
    
    //-----------------------------operations on registers----------------------
    public void incrementProgramCounter()
    {
        setProgramCounter( getProgramCounter() + Integer.BYTES );
    } // end method incrementProgramCounter
    
    //-----------------------------execute--------------------------------------
    public int execute()
    {   
        // do the operation according to the operation code register value
        switch ( getOperationCode() )
        {
            //--------------------DECLARATIONS----------------------------------
            
            case BYTE:
                //ram.put( getDataIndex(), (byte)getOperand() );
                setDataIndex( getDataIndex() + Byte.BYTES );
                return BYTE;
                
            case SHORT:
                //ram.putShort( getDataIndex(), (short)getOperand() );
                setDataIndex( getDataIndex() + Short.BYTES );
                return SHORT;
                
            case CHAR:
                //ram.putChar( getDataIndex(), (char)getOperand() );
                setDataIndex( getDataIndex() + Character.BYTES );
                return CHAR;
                
            case INT:
                //ram.putInt( getDataIndex(), getOperand() );
                setDataIndex( getDataIndex() + Integer.BYTES );
                return INT;
            
            case LONG:
                //ram.putLong( getDataIndex(), getOperand() );
                setDataIndex( getDataIndex() + Long.BYTES );
                return LONG;
            
            case FLOAT:
                //ram.putFloat( getDataIndex(), 
                  // Float.intBitsToFloat( getOperand() ) );
                setDataIndex( getDataIndex() + Float.BYTES );
                return FLOAT;
            
            case DOUBLE:
                //ram.putDouble( getDataIndex(), 
                  // Float.intBitsToFloat( getOperand() ) );
                setDataIndex( getDataIndex() + Double.BYTES );
                return DOUBLE;
            case CHARS:
                setDataIndex( getDataIndex() + Byte.BYTES );
                //ram.putChar( getOperand() + , '\0' );
                return CHARS;
            
            
            //-----------------------OPERATION TYPE-----------------------------
            case TYPE:
                setCalculationType( (byte)getOperand() );
                return TYPE;  
                
            //---------------------INPUT/OUTPUT----------------------
            
            case READ:
                switch ( getCalculationType() )
                {
                    case BYTE:
                        ram.put( getOperand(), input.nextByte() );
                        return READ;
                    case SHORT:
                        ram.putShort( getOperand(), input.nextShort() );
                        return READ;
                    case CHAR:
                        ram.putChar( getOperand(), 
                           input.nextLine().charAt( 0 ) );
                        return READ;
                    case INT:
                        ram.putInt( getOperand(), input.nextInt() );
                        return READ;
                    case LONG:
                        ram.putLong( getOperand(), input.nextLong() );
                        return READ;
                    case FLOAT:
                        ram.putFloat( getOperand(), input.nextFloat() );
                        return READ;
                    case DOUBLE:
                        ram.putDouble( getOperand(), input.nextDouble() );
                        return READ;
                    case CHARS:
                        strings.replace( getOperand(), input.nextLine() );
                        return READ;
                } // end inner switch
                
            case WRITE:
                switch ( getCalculationType() )
                {
                    case BYTE:
                        output.print( ram.get( getOperand() ) );
                        return WRITE;
                    case SHORT:
                        output.print( ram.getShort( getOperand() ) );
                        return WRITE;
                    case CHAR:
                        output.printf( "%c", ram.getChar( getOperand() ) );
                        return WRITE;
                    case INT:
                        output.print( ram.getInt( getOperand() ) );
                        return WRITE;
                    case LONG:
                        output.print( ram.getLong( getOperand() ) );
                        return WRITE;
                    case FLOAT:
                        output.printf( "%." + getPrecision() + "f" , 
                           ram.getFloat( getOperand() ) );
                        return WRITE;
                    case DOUBLE:
                        output.printf( "%." + getPrecision() + "f" , 
                           ram.getDouble( getOperand() ) );
                        return WRITE;
                    case CHARS:
                        output.print( strings.get( getOperand() ) );
                        return WRITE;
                } // end inner switch
                
            case WRITENL:
                output.println();
                return WRITENL;
            
            case PRECISION:
                byte prc = (byte) getOperand();
                setPrecision( prc >= 1 && prc <= 15 ? prc : 2 );
                return PRECISION;
            
            //--------------------------LOAD/STORE------------------------------
            case LOAD:
                switch( getCalculationType() )
                {
                    case BYTE:
                        setAccum( ram.get( getOperand() ) );
                        return LOAD;
                    case SHORT:
                        setAccum( ram.getShort( getOperand() ) );
                        return LOAD;
                    case CHAR:
                        setAccum( ram.getChar( getOperand() ) );
                        return LOAD;
                    case INT:
                        setAccum( ram.getInt( getOperand() ) );
                        return LOAD;
                    case LONG:
                        setAccum( ram.getLong( getOperand() ) );
                        return LOAD;
                    case FLOAT:
                        setAccum( ram.getFloat( getOperand() ) );
                        return LOAD;
                    case DOUBLE:
                        setAccum( ram.getDouble( getOperand() ) );
                        return LOAD;
                    case CHARS:
                        setAccum( getOperand() );
                } // end switch
                
            case STORE:
                switch( getCalculationType() )
                {
                    case BYTE:
                        ram.put( getOperand(), (byte)getAccum() );
                        return STORE;
                    case SHORT:
                        ram.putShort( getOperand(), (short)getAccum() );
                        return STORE;
                    case CHAR:
                        ram.putChar( getOperand(), (char)getAccum() );
                        return STORE;
                    case INT:
                        ram.putInt( getOperand(), (int)getAccum() );
                        return STORE;
                    case LONG:
                        ram.putLong( getOperand(), (long)getAccum() );
                        return STORE;
                    case FLOAT:
                        ram.putFloat( getOperand(), (float)getAccum() );
                        return STORE;
                    case DOUBLE:
                        ram.putDouble( getOperand(), getAccum() );
                        return STORE;
                    case CHARS:
                        switch ( getPreviousCaltype() )
                        {
                            case BYTE:
                                strings.replace( getOperand(),
                                   String.valueOf ( (byte)getAccum() ) );
                                break;
                            case SHORT:
                                strings.replace( getOperand(),
                                   String.valueOf ( (short)getAccum() ) );
                                break;
                            case CHAR:
                                strings.replace( getOperand(),
                                   String.valueOf ( (char)getAccum() ) );
                                break;
                            case INT:
                                strings.replace( getOperand(),
                                   String.valueOf ( (int)getAccum() ) );
                                break;
                            case LONG:
                                strings.replace( getOperand(),
                                   String.valueOf ( (long)getAccum() ) );
                                break;
                            case FLOAT:
                                strings.replace( getOperand(),
                                   String.valueOf ( (float)getAccum() ) );
                                break;
                            case DOUBLE:
                                strings.replace( getOperand(),
                                   String.valueOf ( getAccum() ) );
                                break;
                            case CHARS:
                                strings.replace( getOperand(),
                                   strings.get( ( int)getAccum() ) );
                                break;
                        } // end switch
                        return STORE;
                } // end switch
                
            //--------------------ARITHMETIC OPERATIONS-------------------------
            case ADD:
                switch( getCalculationType() )
                {
                    case BYTE:
                        setAccum( getAccum() + ram.get( getOperand() ) );
                        return ADD;
                    case SHORT:
                        setAccum( getAccum() + ram.getShort( getOperand() ) );
                        return ADD;
                    case CHAR:
                        setAccum( getAccum() + ram.getChar( getOperand() ) );
                        return ADD;
                    case INT:
                        setAccum( getAccum() + ram.getInt( getOperand() ) );
                        return ADD;
                    case LONG:
                        setAccum( getAccum() + ram.getLong( getOperand() ) );
                        return ADD;
                    case FLOAT:
                        setAccum( getAccum() + ram.getFloat( getOperand() ) );
                        return ADD;
                    case DOUBLE:
                        setAccum( getAccum() + ram.getDouble( getOperand() ) );
                        return ADD;
                    case CHARS:
                        int oprd = getOperand();
                        String newStr = strings.get( oprd );
                        
                        switch ( getPreviousCaltype() )
                        {
                            case BYTE:
                                strings.replace( 
                                   oprd, newStr + (byte)getAccum() );
                                break;
                            case SHORT:
                                strings.replace( 
                                   oprd, newStr + (short)getAccum() );
                                break;
                            case CHAR:
                                strings.replace( 
                                   oprd, newStr + (char)getAccum() );
                                break;
                            case INT:
                                strings.replace( 
                                   oprd, newStr + (int)getAccum() );
                                break;
                            case LONG:
                                strings.replace( 
                                   oprd, newStr + (long)getAccum() );
                                break;
                            case FLOAT:
                                strings.replace( 
                                   oprd, newStr + (float)getAccum() );
                                break;
                            case DOUBLE:
                                strings.replace( 
                                   oprd, newStr + (double)getAccum() );
                                break;
                            case CHARS:
                                strings.replace( 
                                   oprd, newStr + (int)getAccum() );
                                break;
                        } // end switch
                        return ADD;
                } // end switch
                
            case SUB:
                switch( getCalculationType() )
                {
                    case BYTE:
                        setAccum( getAccum() - ram.get( getOperand() ) );
                        return SUB;
                    case SHORT:
                        setAccum( getAccum() - ram.getShort( getOperand() ) );
                        return SUB;
                    case CHAR:
                        setAccum( getAccum() - ram.getChar( getOperand() ) );
                        return SUB;
                    case INT:
                        setAccum( getAccum() - ram.getInt( getOperand() ) );
                        return SUB;
                    case LONG:
                        setAccum( getAccum() - ram.getLong( getOperand() ) );
                        return SUB;
                    case FLOAT:
                        setAccum( getAccum() - ram.getFloat( getOperand() ) );
                        return SUB;
                    case DOUBLE:
                        setAccum( getAccum() - ram.getDouble( getOperand() ) );
                        return SUB;
                    case CHARS:
                        throw new RuntimeException( "operator - with string" );
                } // end switch
            
            case DIV:
                switch( getCalculationType() )
                {
                    case BYTE:
                        setAccum( getAccum() / ram.get( getOperand() ) );
                        return DIV;
                    case SHORT:
                        setAccum( getAccum() / ram.getShort( getOperand() ) );
                        return DIV;
                    case CHAR:
                        setAccum( getAccum() / ram.getChar( getOperand() ) );
                        return DIV;
                    case INT:
                        setAccum( getAccum() / ram.getInt( getOperand() ) );
                        return DIV;
                    case LONG:
                        setAccum( getAccum() / ram.getLong( getOperand() ) );
                        return DIV;
                    case FLOAT:
                        setAccum( getAccum() / ram.getFloat( getOperand() ) );
                        return DIV;
                    case DOUBLE:
                        setAccum( getAccum() / ram.getDouble( getOperand() ) );
                        return DIV;
                    case CHARS:
                        throw new RuntimeException( "operator / with string" );
                } // end switch
            
            case MUL:
                switch( getCalculationType() )
                {
                    case BYTE:
                        setAccum( getAccum() * ram.get( getOperand() ) );
                        return MUL;
                    case SHORT:
                        setAccum( getAccum() * ram.getShort( getOperand() ) );
                        return MUL;
                    case CHAR:
                        setAccum( getAccum() * ram.getChar( getOperand() ) );
                        return MUL;
                    case INT:
                        setAccum( getAccum() * ram.getInt( getOperand() ) );
                        return MUL;
                    case LONG:
                        setAccum( getAccum() * ram.getLong( getOperand() ) );
                        return MUL;
                    case FLOAT:
                        setAccum( getAccum() * ram.getFloat( getOperand() ) );
                        return MUL;
                    case DOUBLE:
                        setAccum( getAccum() * ram.getDouble( getOperand() ) );
                        return MUL;
                    case CHARS:                        
                        switch ( getPreviousCaltype() )
                        {
                            case BYTE:                                
                            case SHORT:
                            case INT:
                            case LONG:
                                int times = (int) getAccum();
                                String str = "";
                                for ( int i = 0; i < times; i++ )
                                {
                                    str += strings.get( getOperand() );
                                } // end for
                                strings.replace( getOperand(), str );
                                break;
                            case FLOAT:
                                throw new RuntimeException( 
                                   "operator * with float and chars operands" );
                            case DOUBLE:
                                throw new RuntimeException( 
                                   "operator * with double and chars operands" );
                            case CHAR:
                                throw new RuntimeException( 
                                   "operator * with char and chars operands" );
                            case CHARS:
                                throw new RuntimeException( 
                                   "operator * with chars and chars operands" );
                        } // end switch
                        return MUL;
                } // end switch   
                
                case MOD:
                switch( getCalculationType() )
                {
                    case BYTE:
                        setAccum( getAccum() % ram.get( getOperand() ) );
                        return MOD;
                    case SHORT:
                        setAccum( getAccum() % ram.getShort( getOperand() ) );
                        return MOD;
                    case CHAR:
                        setAccum( getAccum() % ram.getChar( getOperand() ) );
                        return MOD;
                    case INT:
                        setAccum( getAccum() % ram.getInt( getOperand() ) );
                        return MOD;
                    case LONG:
                        setAccum( getAccum() % ram.getLong( getOperand() ) );
                        return MOD;
                    case FLOAT:
                        setAccum( getAccum() % ram.getFloat( getOperand() ) );
                        return MOD;
                    case DOUBLE:
                        setAccum( getAccum() % ram.getDouble( getOperand() ) );
                        return MOD;
                    case CHARS:                        
                        throw new RuntimeException( 
                            "operator % with chars" );
                } // end switch  
            
            case INC:
                switch( getCalculationType() )
                {
                    case BYTE:
                        ram.put( getOperand(), (byte) (ram.get( getOperand() ) + 1 ) );
                        return INC;
                    case SHORT:
                        ram.putShort( getOperand(), (short) (ram.getShort( getOperand() ) + 1 ) );
                        return INC;
                    case CHAR:
                        ram.putChar( getOperand(), (char) (ram.getChar( getOperand() ) + 1 ) );
                        return INC;
                    case INT:
                        ram.putInt( getOperand(), (ram.getInt( getOperand() ) + 1 ) );
                        return INC;
                    case LONG:
                        ram.putLong( getOperand(), (ram.getLong( getOperand() ) + 1 ) );
                        return INC;
                    case FLOAT:
                        ram.putFloat( getOperand(), (ram.getFloat( getOperand() ) + 1.0f ) );
                        return INC;
                    case DOUBLE:
                        ram.putDouble( getOperand(), (ram.getDouble( getOperand() ) + 1.0 ) );
                        return INC;
                    case CHARS:                        
                        throw new RuntimeException( 
                            "operator ++ with chars" );
                } // end switch
            
            case DEC:
                switch( getCalculationType() )
                {
                    case BYTE:
                        ram.put( getOperand(), (byte) (ram.get( getOperand() ) - 1 ) );
                        return DEC;
                    case SHORT:
                        ram.putShort( getOperand(), (short) (ram.getShort( getOperand() ) - 1 ) );
                        return DEC;
                    case CHAR:
                        ram.putChar( getOperand(), (char) (ram.getChar( getOperand() ) - 1 ) );
                        return DEC;
                    case INT:
                        ram.putInt( getOperand(), (ram.getInt( getOperand() ) - 1 ) );
                        return DEC;
                    case LONG:
                        ram.putLong( getOperand(), (ram.getLong( getOperand() ) - 1 ) );
                        return DEC;
                    case FLOAT:
                        ram.putFloat( getOperand(), (ram.getFloat( getOperand() ) - 1.0f ) );
                        return DEC;
                    case DOUBLE:
                        ram.putDouble( getOperand(), (ram.getDouble( getOperand() ) - 1.0 ) );
                        return DEC;
                    case CHARS:                        
                        throw new RuntimeException( 
                            "operator -- with chars" );
                } // end switch
            
            //------------------------------BANCHING----------------------------
            case BRANCH:
                setProgramCounter( getOperand() );
                break;
            case BRANCHNEG:
                if ( getAccum() < 0 )
                    setProgramCounter( getOperand() );
                break;
            case BRANCHZERO:
                if ( (long) getAccum() == 0 )
                    setProgramCounter( getOperand() );
                break;
            case HALT:
                return HALT;
        } // end switch
        
        // unsuccessful execution of current instruction
        return WRONG_EXECUTION;
    } // end method execute
} // end class ExecutionUnit
