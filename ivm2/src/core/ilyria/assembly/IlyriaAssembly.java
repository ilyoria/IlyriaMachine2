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
package core.ilyria.assembly;
import core.ilyria.machine.IlyriaMachine;
import core.ilyria.machine.unit.CPU;
import core.ilyria.machine.unit.ExecutionUnit;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 *
 * @author ilyria
 */
public class IlyriaAssembly implements IlyriaMachine
{
    private Scanner input;
    private PrintStream output;
    private Path source;
    
    private CPU cpu;
    
    private HashMap< String, Integer > values = new HashMap<>();
    private HashMap< String, Integer > labels = new HashMap<>();

    public IlyriaAssembly( Path source, CPU cpu )
    {
        this.source = source;
        this.cpu = cpu;
        
        if ( source.endsWith( ".ima" ) )
        {
            throw new RuntimeException( "source must end with .ima" );
        } // end if
        
        try
        {
            input = new Scanner( Files.newInputStream( source ) );
        } // end try
        catch ( IOException ex )
        {
            System.err.println( ex.getMessage() );
        } // end catch
        
        Path outPath = Paths.get( source.toString().substring(
            0, source.toString().lastIndexOf( "." ) )
            + ".im" );
        
        try
        {
            output = new PrintStream( Files.newOutputStream( outPath,
               StandardOpenOption.TRUNCATE_EXISTING,
               StandardOpenOption.CREATE,
               StandardOpenOption.WRITE ) );
        } // end try
        catch ( IOException ex )
        {
            ex.printStackTrace();
            System.err.println( ex.getMessage() );
        } // end catch
    } // end default constructor
    
    private String readNextLine()
    {
        String line = input.nextLine();
        if ( line.isEmpty() || line.startsWith( "#" ) ||
            line.startsWith( "//" ) )
            return readNextLine();
        
        return line;
    } // end method readNextLine
    
    private String[] splitLine( String line )
    {
        StringTokenizer lineTokens = new StringTokenizer( line, " ", false );
        String tokens[] = new String[ lineTokens.countTokens() ];
        
        for ( int i = 0; i < tokens.length; ++i )
            tokens[ i ] = lineTokens.nextToken();
        
        if ( tokens.length < 2 )
            tokens = new String[]{ tokens[ 0 ], String.format(
               "%0" + cpu.getExecutionUnit().INSTRUCTION_LENGTH() + "d", 0) };
        
        return tokens;
    } // end method spliteLine
    
    private String[] translateOperations( String[] line )
    {
        switch ( line[ 0 ].toUpperCase() )
        {
            //----------------------------DECLARATIONS--------------------------
            case "BYTE":case "ثماني":
                line[ 0 ] = "+01";
                break;
            case "SHORT":case "قصير":
                line[ 0 ] = "+02";
                break;
            case "CHAR":case "حرفي":
                line[ 0 ] = "+03";
                break;
            case "INT":case "صحيح":
                line[ 0 ] = "+04";
                break;
            case "LONG":case "طويل":
                line[ 0 ] = "+05";
                break;
            case "FLOAT":case "عائم":
                line[ 0 ] = "+06";
                break;
            case "DOUBLE":case "مزدوج":
                line[ 0 ] = "+07";
                break;
            case "CHARS":case "نصي":
                line[ 0 ] = "+08";
                break;
            case "TYPE":case "نوع":
                line[ 0 ] = "+09";
                break;
                
            //---------------------------INPUT/OUTPUT---------------------------
            case "READ":case "اقرا":
                line[ 0 ] = "+10";
                break;
            case "WRITE":case "اكتب":
                line[ 0 ] = "+11";
                break;
            case "WRITENL":case "اكتبسج":
                line[ 0 ] = "+12";
                break;
            case "PRECISION":case "دقة":
                line[ 0 ] = "+13";
                break;
                
            //-------------------------LOAD/STORE-------------------------------
            case "LOAD":case "MOVE":case "حمل":case "حرك":
                line[ 0 ] = "+20";
                break;
            case "STORE":case "خزن":
                line[ 0 ] = "+21";
                break;
            
            case "ASIGN":case "اسند":
                line[ 0 ] = "+22";
                break;
             
            //--------------------------ARITHMETIC------------------------------
            case "ADD":case "جمع":
                line[ 0 ] = "+30";
                break;
            case "SUB":case "طرح":
                line[ 0 ] = "+31";
                break;
            case "DIV":case "قسمة":
                line[ 0 ] = "+32";
                break;
            case "MUL":case "ضرب":case "جداء":
                line[ 0 ] = "+33";
                break;
            case "MOD":case "باقي":
                line[ 0 ] = "+34";
                break;
            case "INC":case "زد":
                line[ 0 ] = "+35";
                break;
            case "DEC":case "نقص":
                line[ 0 ] = "+36";
                break;
                
            //----------------------CONTROL'S TRANSFER--------------------------
            case "BRANCH":case "تقاطع":
                line[ 0 ] = "+40";
                break;
            case "BRANCHNEG":case "تقاطعسلب":
                line[ 0 ] = "+41";
                break;
            case "BRANCHZERO":case "تقاطعصفر":
                line[ 0 ] = "+42";
                break;
            case "HALT":case "انهاء":case "ايقاف":
                line[ 0 ] = "+43";
                break;
            
            //----------------------------MATH----------------------------------
            case "EXP":case "اس":
                line[ 0 ] = "+50";
                break;
            case "SQRT":case "جذر":
                line[ 0 ] = "+51";
                break;
                
            //---------------------------LABEL/PROCEDURE------------------------
            case "LABEL":case "ملصق":
                line[ 0 ] = "+70";
                break;
            case "PROCEDURE":case "اجراء":
                line[ 0 ] = "+71";
                break;
            case "RETURN":case "رجوع":
                line[ 0 ] = "+72";
                break;
            case "CALL":case "استدعاء":
                line[ 0 ] = "+73";
                break;
            case "FRAME":case "اطار":
                line[ 0 ] = "+74";
                break;
                
            //----------------------------MEMORY--------------------------------
            case "DELETE":case "حذف":
                line[ 0 ] = "+80";
                break;
        } // end switch
        
        return line;
    } // end method translateOperations
    
    private String[] translateValues( String[] operationLine )
    {
        int operation = Integer.parseInt( operationLine[ 0 ] );
        
        if (  operation == LABEL ||
           operation == BRANCH ||
           operation == BRANCHNEG ||
           operation == BRANCHZERO )
            return operationLine;
        
        final ExecutionUnit exec = cpu.getExecutionUnit();
        
        // there is more strings put it on one
        if ( operationLine.length >= 3 )
        {
            StringBuilder builder = new StringBuilder();
            
            for ( int i = 2; i < operationLine.length; i++ )
            {
                builder.append( operationLine[ i ] );
                if ( i < operationLine.length - 1)
                    builder.append( " " );
            } // end for
            
            String str = builder.toString().trim();
            //System.out.println("str = '" + str + "'");
            if ( str.length() == 2 )
            {
                if ( str.charAt( 0 ) == '"' &&
                   str.charAt( 1 ) == '"' )
                    str = "";
            } // end if   
            else if ( str.startsWith( "\"" ) &&
               str.endsWith( "\"" ) )
                str = str.substring( 1, str.length() - 1 );
            
            operationLine = new String[]{
                operationLine[ 0 ],
                operationLine[ 1 ],
                str };
        } // end if
        
        //-----------------------CALTYPE----------------------------------------
        byte calculationType = (byte)Integer.parseInt( operationLine[ 0 ] );
        if ( calculationType == TYPE )
        {
            switch ( operationLine[ 1 ].toUpperCase() )
            {
                case "BYTE":case "ثماني":
                    operationLine[ 1 ] = String.valueOf( BYTE );
                    cpu.getExecutionUnit().setCalculationType( (byte)BYTE );
                    break;
                case "SHORT":case "قصير":
                    operationLine[ 1 ] = String.valueOf( SHORT );
                    cpu.getExecutionUnit().setCalculationType( (byte)SHORT );
                    break;
                case "CHAR":case "حرفي":
                    operationLine[ 1 ] = String.valueOf( CHAR );
                    cpu.getExecutionUnit().setCalculationType( (byte)CHAR );
                    break;
                case "INT":case "صحيح":
                    operationLine[ 1 ] = String.valueOf( INT );
                    cpu.getExecutionUnit().setCalculationType( (byte)INT );
                    break;
                case "LONG":case "طويل":
                    operationLine[ 1 ] = String.valueOf( LONG );
                    cpu.getExecutionUnit().setCalculationType( (byte)LONG );
                    break;
                case "FLOAT":case "عائم":
                    operationLine[ 1 ] = String.valueOf( FLOAT );
                    cpu.getExecutionUnit().setCalculationType( (byte)FLOAT );
                    break;
                case "DOUBLE":case "مزدوج":
                    operationLine[ 1 ] = String.valueOf( DOUBLE );
                    cpu.getExecutionUnit().setCalculationType( (byte)DOUBLE );
                    break;
                case "CHARS":case "نصي":
                    operationLine[ 1 ] = String.valueOf( CHARS );
                    cpu.getExecutionUnit().setCalculationType( (byte)CHARS );
                    break;
            } // end switch
            
        } // end if
        
        //-----------------------values to address------------------------------
        if ( values.containsKey( operationLine[ 1 ] ) )
        {
            operationLine[ 1 ] = String.valueOf( 
               values.get( operationLine[ 1 ] ) );
        } // end if
        else
        {
            int dataIndex = exec.getDataIndex();
            int optcode = Integer.parseInt( operationLine[ 0 ] );
            
            switch ( optcode )
            {
                case BYTE:
                    values.put( operationLine[ 1 ], dataIndex );
                    exec.setDataIndex( dataIndex + Byte.BYTES );
                    break;
                case SHORT:
                    values.put( operationLine[ 1 ], dataIndex );
                    exec.setDataIndex( dataIndex + Short.BYTES );
                    break;
                case CHAR:
                    values.put( operationLine[ 1 ], dataIndex );
                    exec.setDataIndex( dataIndex + Character.BYTES );
                    break;
                case INT:
                    values.put( operationLine[ 1 ], dataIndex );
                    exec.setDataIndex( dataIndex + Integer.BYTES );
                    break;
                case LONG:
                    values.put( operationLine[ 1 ], dataIndex );
                    exec.setDataIndex( dataIndex + Long.BYTES );
                    break;
                case FLOAT:
                    values.put( operationLine[ 1 ], dataIndex );
                    exec.setDataIndex( dataIndex + Float.BYTES );
                    break;
                case DOUBLE:
                    values.put( operationLine[ 1 ], dataIndex );
                    exec.setDataIndex( dataIndex + Double.BYTES );
                    break;
                case CHARS:
                    values.put( operationLine[ 1 ], dataIndex );
                    // data index will incremented on assign
                    exec.setDataIndex( dataIndex + Double.BYTES );
                    break;
            } // end switch
            
            if ( optcode >= BYTE && optcode <= CHARS )
                operationLine[ 1 ] = String.valueOf( 
                    values.get( operationLine[ 1 ] ) );
        } // end else...if
        
        // ----------------------------ASIGN------------------------------------
        
        int address = Integer.parseInt( operationLine[ 1 ] ); 
        switch ( Integer.parseInt( operationLine[ 0 ] ) )
        {
            case BYTE:
                cpu.getRam().put( address, (byte)Integer.parseInt( operationLine[ 2 ] ) );
                break;
            case SHORT:
                cpu.getRam().putShort( address, (short)Integer.parseInt( operationLine[ 2 ] ) );
                break;
            case CHAR:
                cpu.getRam().putChar( address, operationLine[ 2 ].charAt( 1 ) );
                break;
            case INT:
                cpu.getRam().putInt( address, Integer.parseInt( operationLine[ 2 ] ) );
                break;
            case LONG:
                cpu.getRam().putLong( address, Long.parseLong( operationLine[ 2 ] ) );
                break;
            case FLOAT:
                cpu.getRam().putFloat( address, Float.parseFloat( operationLine[ 2 ] ) );
                break;
            case DOUBLE:
                cpu.getRam().putDouble( address, Double.parseDouble( operationLine[ 2 ] ) );
                break;
            case CHARS:
                exec.getStrings().put( address, operationLine[ 2 ]
                   .replace( "\\n", "\n" )
                   .replace( "\\t", "\t" ) 
                   .replace( "\\r", "\r" )
                   .replace( "\\ج", "\n" )
                   .replace( "\\ف", "\t" ) 
                   .replace( "\\ع", "\r" ) );
                cpu.getRam().put( address, (byte)0 );
                break;
        } // end switch
        
        //------------------------operand formating-----------------------------
        operationLine[ 1 ] = String.format( 
           "%0" + exec.INSTRUCTION_LENGTH() + "d", Integer.parseInt( 
              operationLine[ 1 ] ) );
        
        // return the translated values instruction
        return operationLine;
    } // end method translateValues
    
    private int currentLabelAddress = 0;
    private String[] translateLabels( String[] optcode )
    {
        if ( Integer.parseInt( optcode[ 0 ] ) != LABEL )
        {
            currentLabelAddress += 4;
            return optcode;
        } // end if            
        
        if ( labels.containsKey( optcode[ 1 ] ) )
        {
            optcode[ 1 ] = String.valueOf( 
               labels.get( optcode[ 1 ] ) );
        } // end if
        else
        {
            labels.put( optcode[ 1 ], currentLabelAddress );
            
            optcode[ 1 ] = String.valueOf( 
               labels.get( optcode[ 1 ] ) );
        } // end if...else
        
        optcode[ 1 ] = String.format( 
           "%0" + cpu.getExecutionUnit().INSTRUCTION_LENGTH() + "d", 
           Integer.parseInt( optcode[ 1 ] ) );
        
        currentLabelAddress += 4;
        
        System.out.println( labels.keySet() );
        
        return optcode;
    } // end method translateLabels
    
    private String[] translateBranches( String[] optcode )
    {
        int operator = Integer.parseInt( optcode[ 0 ] );
        
        if ( operator != BRANCH )
            if ( operator != BRANCHNEG )
                if ( operator != BRANCHZERO )
                    return optcode;
        
        optcode[ 1 ] = String.valueOf( 
               labels.get( optcode[ 1 ] ) );
               
        optcode[ 1 ] = String.format( 
           "%0" + cpu.getExecutionUnit().INSTRUCTION_LENGTH() + "d", 
           Integer.parseInt( optcode[ 1 ] ) );
        
        return optcode;
    } // end method translateBranches
    
    
    public static void main( String[] args ) throws IOException
    {
        CPU cpu = new CPU();
        
        IlyriaAssembly asmb = new IlyriaAssembly( Paths.get( "ilyria.ima" ), cpu );
        List< String[]> list = Files.lines( asmb.source )
           .filter( e -> !e.isEmpty() )
           .filter( e ->  !e.startsWith( "//" ) )
           .filter( e -> !e.startsWith( "#" ) )
           .map( asmb::splitLine )
           .map( asmb::translateOperations )
           .map( asmb::translateValues )
           .map( asmb::translateLabels )
           //.map( asmb::translateBranches )
           .collect( Collectors.toList() );
        
        list = list.stream()
           .map( asmb::translateBranches )
           .collect( Collectors.toList() );
        
        for ( String[] tro : list )
        {
            //System.out.println( "++>" + Arrays.toString( tro ) );
            //String[] trv = asmb.translateValues( tro );
            //System.out.println( "-->" + Arrays.toString( tro ) );
            asmb.output.printf( "%s%s%n", tro[ 0 ], tro[ 1 ] );
        } // end for*/
        
        cpu.getExecutionUnit().setDataIndex( cpu.getExecutionUnit().END_INSTRUCTION() );
        
        //System.out.println( "\n-------------------------------" );
        
        Files.lines( Paths.get( "ilyria.im" ) )
           .map( Integer::parseInt )
           .forEach( e ->
            {
                //System.out.println( e );
                cpu.getRam().putInt( e );
            });
        
        //Scanner sc = new Scanner( System.in );
        
        
        int returnValue = -1;
        while ( returnValue != IlyriaMachine.HALT )
        {
            //System.out.println( "Fetch: " + cpu.fetch() );
            //System.out.println( "decode: " + Arrays.toString( cpu.decode() ) );
            cpu.fetch();
            cpu.decode();
            returnValue = cpu.execute();
            //System.err.println( cpu.getExecutionUnit().getDataIndex() );
            //System.out.println( "execute: " + returnValue );
            
            //sc.nextLine();
        } // end while//*/
        
        
        //System.out.println( cpu.getRam().get(2796202 ));
    } // end main
} // end class IlyriaAssembly
