package fi.soveltia.liferay.gsearch.elasticsearch.internal.query;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.DecayFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ExponentialDecayFunctionBuilder;
import org.elasticsearch.index.query.functionscore.GaussDecayFunctionBuilder;
import org.elasticsearch.index.query.functionscore.LinearDecayFunctionBuilder;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.search.MultiValueMode;

import org.osgi.service.component.annotations.Component;

import fi.soveltia.liferay.gsearch.elasticsearch.query.DecayFunctionScoreQueryTranslator;
import fi.soveltia.liferay.gsearch.query.DecayFunctionScoreQuery;

/**
 * GSearch Decay Funtion Score query translator impl.
 * 
 * @author Petteri Karttunen
 *
 */
@Component(
	immediate=true,
	service = DecayFunctionScoreQueryTranslator.class
)
public class DecayFunctionScoreQueryTranslatorImpl implements DecayFunctionScoreQueryTranslator {

	public QueryBuilder translate(DecayFunctionScoreQuery query) {
		

		DecayFunctionBuilder decayFunctionBuilder;

		if (query.getFunctionType().equals("exp")) {

			decayFunctionBuilder = new ExponentialDecayFunctionBuilder(query.getFieldName(), query.getOrigin(),
					query.getScale(), query.getOffset(), query.getDecay());
		} else if (query.getFunctionType().equals("linear")) {

			decayFunctionBuilder = new LinearDecayFunctionBuilder(query.getFieldName(), query.getOrigin(),
					query.getScale(), query.getOffset(), query.getDecay());
		} else {

			decayFunctionBuilder = new GaussDecayFunctionBuilder(query.getFieldName(), query.getOrigin(),
					query.getScale(), query.getOffset(), query.getDecay());
		}
		
		// Multivalue mode
		
		if (query.getMultiValueMode() != null) {
			
			if (query.getMultiValueMode().equals("avg")) {

				decayFunctionBuilder.setMultiValueMode(MultiValueMode.AVG);

			} else if (query.getMultiValueMode().equals("max")) {
				
				decayFunctionBuilder.setMultiValueMode(MultiValueMode.MAX);

			} else if (query.getMultiValueMode().equals("min")) {

				decayFunctionBuilder.setMultiValueMode(MultiValueMode.MIN);

			} else if (query.getMultiValueMode().equals("sum")) {

				decayFunctionBuilder.setMultiValueMode(MultiValueMode.SUM);
			}
		}

		FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(decayFunctionBuilder);

		functionScoreQueryBuilder.boost(query.getBoost());
		
		if (query.getBoostMode() != null) {
			functionScoreQueryBuilder.boostMode(CombineFunction.valueOf(query.getBoostMode()));
		}

		if (query.getScoreMode() != null) {
			functionScoreQueryBuilder.scoreMode(FunctionScoreQuery.ScoreMode.valueOf(query.getScoreMode()));
		}

		if (query.getMaxBoost() != null) {
			functionScoreQueryBuilder.maxBoost(query.getMaxBoost());
		}

		if (query.getMinScore() != null) {
			functionScoreQueryBuilder.setMinScore(query.getMinScore());
		}
	
		return functionScoreQueryBuilder;
	}
}
