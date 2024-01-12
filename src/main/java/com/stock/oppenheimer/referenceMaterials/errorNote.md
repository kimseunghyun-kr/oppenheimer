make sure that you are properly setting the stockData field before saving a MarketData entity.

java.lang.AssertionError: expectation "expectNextCount(1)" failed (expected: count = 1; actual: counted = 0; signal: onError(org.springframework.dao.DataIntegrityViolationException: could not execute statement [NULL not allowed for column "STOCK_TICKER_ID"; SQL statement:
insert into market_data (adj_close,adj_open,adj_volume,close,dividend,high,low,open,split_factor,stock_ticker_id,trade_date,volume,id) values (?,?,?,?,?,?,?,?,?,?,?,?,?) [23502-214]] [insert into market_data (adj_close,adj_open,adj_volume,close,dividend,high,low,open,split_factor,stock_ticker_id,trade_date,volume,id) values (?,?,?,?,?,?,?,?,?,?,?,?,?)]; SQL [insert into market_data (adj_close,adj_open,adj_volume,close,dividend,high,low,open,split_factor,stock_ticker_id,trade_date,volume,id) values (?,?,?,?,?,?,?,?,?,?,?,?,?)]; constraint [null]))

is having the DTO class mirroring the relationship, dependencies of its corresponding entity class valid??
when DTO classes are supposed to be independent??



in a reactive context, assuing that the method is part of a transaction, would it be better to bubble up a runtimeException to void the transaction itself or generate an empty Mono and roll back should an empty mono be returned?

ChatGPT
In a reactive context, the approach to handle exceptions and transactional behavior can differ from traditional synchronous programming. Both bubbling up a RuntimeException and returning an empty Mono are valid strategies, but they serve slightly different purposes.

Throwing a RuntimeException:
Bubbling up a RuntimeException can indicate a more severe issue that requires the entire transaction to be rolled back. In a reactive scenario, this could lead to a global transaction rollback, which might not be ideal if the exception is specific to a particular step within the reactive chain. While this approach provides a clear way to signal a failure and trigger a rollback, it might impact the granularity of error handling in your reactive flow.

Returning an Empty Mono:
Returning an empty Mono is a reactive way of signaling that a step in the chain couldn't be completed successfully. It doesn't necessarily mean that the entire transaction should be rolled back. Instead, it allows for more fine-grained error handling within the reactive flow. If you need to void a transaction based on a specific condition, returning an empty Mono from a reactive operation within that transactional scope can achieve that. This approach aligns well with the principles of reactive programming, providing better control over error handling and flow.

In your case, if you want to void a specific part of a transaction based on a condition (such as the inability to convert stockDataDTO), returning an empty Mono seems like a more appropriate choice. This approach allows you to continue the reactive flow while clearly indicating the failure of that step.

Remember that the choice depends on your specific requirements, the granularity of error handling you want, and the overall design of your reactive chain. Both approaches can work, but using an empty Mono to indicate a step's failure is more aligned with reactive programming principles and provides better flexibility in handling different error scenarios.


https://jiwhunkim.github.io/develop/2020/02/01/spring-using-together-jpa-and-r2dbc.html


# create then drop entire schema post session -> create-drop
# override create each time -> create
# update from existing -> update


