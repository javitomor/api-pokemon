package com.example.apipokemon.configuration;

import javax.sql.DataSource;

import com.example.apipokemon.enums.SortFieldsPokemonEnum;
import com.example.apipokemon.model.Pokemon;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Value("classPath:/data/pokemon.csv")
    private Resource inputResource;

    @Value("${csv.pokemon.chunk.size}")
    private int csvPokemonChunkSize;

    @Bean
    public Job readCSVFileJob() {
        return jobBuilderFactory
                .get("readCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .start(loadCsvStep())
                .build();
    }

    @Bean
    public Step loadCsvStep() {
        return stepBuilderFactory
                .get("loadCsvStep")
                .<Pokemon, Pokemon>chunk(csvPokemonChunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemProcessor<Pokemon, Pokemon> processor() {
        return new DBLogProcessor();
    }

    @Bean
    public FlatFileItemReader<Pokemon> reader() {
        FlatFileItemReader<Pokemon> itemReader = new FlatFileItemReader<Pokemon>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(0);
        itemReader.setResource(inputResource);
        return itemReader;
    }

    @Bean
    public LineMapper<Pokemon> lineMapper() {
        DefaultLineMapper<Pokemon> lineMapper = new DefaultLineMapper<Pokemon>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        BeanWrapperFieldSetMapper<Pokemon> fieldSetMapper = new BeanWrapperFieldSetMapper<Pokemon>();

        lineTokenizer.setNames(getNamesLineTokenizer());
        lineTokenizer.setIncludedFields(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});

        fieldSetMapper.setTargetType(Pokemon.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }


    private String[] getNamesLineTokenizer() {
        return new String[]{
                SortFieldsPokemonEnum.NUMERO.getColumn(),
                SortFieldsPokemonEnum.NAME.getColumn(),
                SortFieldsPokemonEnum.TYPE1.getColumn(),
                SortFieldsPokemonEnum.TYPE2.getColumn(),
                SortFieldsPokemonEnum.TOTAL.getColumn(),
                SortFieldsPokemonEnum.HP.getColumn(),
                SortFieldsPokemonEnum.ATTACK.getColumn(),
                SortFieldsPokemonEnum.DEFENSE.getColumn(),
                SortFieldsPokemonEnum.SP_ATK.getColumn(),
                SortFieldsPokemonEnum.SP_DEF.getColumn(),
                SortFieldsPokemonEnum.SPEED.getColumn(),
                SortFieldsPokemonEnum.GENERATION.getColumn(),
                SortFieldsPokemonEnum.LEGENDARY.getColumn()
        };
    }

    @Bean
    public JdbcBatchItemWriter<Pokemon> writer() {
        JdbcBatchItemWriter<Pokemon> itemWriter = new JdbcBatchItemWriter<Pokemon>();
        itemWriter.setDataSource(dataSourcePokemon());
        itemWriter.setSql(getSqlInsertPokemon());
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Pokemon>());
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }


    private String getSqlInsertPokemon() {
        String sql = "insert into pokemon (" +
                SortFieldsPokemonEnum.NUMERO.getColumn() +","+
                SortFieldsPokemonEnum.NAME.getColumn() +","+
                SortFieldsPokemonEnum.TYPE1.getColumn() +","+
                SortFieldsPokemonEnum.TYPE2.getColumn() +","+
                SortFieldsPokemonEnum.TOTAL.getColumn() +","+
                SortFieldsPokemonEnum.HP.getColumn() +","+
                SortFieldsPokemonEnum.ATTACK.getColumn() +","+
                SortFieldsPokemonEnum.DEFENSE.getColumn() +","+
                SortFieldsPokemonEnum.SP_ATK.getColumn() +","+
                SortFieldsPokemonEnum.SP_DEF.getColumn() +","+
                SortFieldsPokemonEnum.SPEED.getColumn() +","+
                SortFieldsPokemonEnum.GENERATION.getColumn() +","+
                SortFieldsPokemonEnum.LEGENDARY.getColumn() +
                ") "+
                " values ("+
                ":"+SortFieldsPokemonEnum.NUMERO.getAttribute() +" , "+
                ":"+SortFieldsPokemonEnum.NAME.getAttribute() +" , "+
                ":"+SortFieldsPokemonEnum.TYPE1.getAttribute()+" , "+
                ":"+SortFieldsPokemonEnum.TYPE2.getAttribute() +" , "+
                ":"+SortFieldsPokemonEnum.TOTAL.getAttribute() +" , "+
                ":"+SortFieldsPokemonEnum.HP.getAttribute() +" , "+
                ":"+SortFieldsPokemonEnum.ATTACK.getAttribute() +" , "+
                ":"+SortFieldsPokemonEnum.DEFENSE.getAttribute() +" , "+
                ":"+SortFieldsPokemonEnum.SP_ATK.getAttribute() +" , "+
                ":"+SortFieldsPokemonEnum.SP_DEF.getAttribute() +" , "+
                ":"+SortFieldsPokemonEnum.SPEED.getAttribute() +" , "+
                ":"+SortFieldsPokemonEnum.GENERATION.getAttribute() +" , "+
                ":"+SortFieldsPokemonEnum.LEGENDARY.getAttribute()+")";
        return sql;

    }

    public DataSource dataSourcePokemon() {

        EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();

        return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .addScript("classpath:dataSource/tablesScripts.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }


}
