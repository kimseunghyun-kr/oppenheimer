make sure that you are properly setting the stockData field before saving a MarketData entity.

java.lang.AssertionError: expectation "expectNextCount(1)" failed (expected: count = 1; actual: counted = 0; signal: onError(org.springframework.dao.DataIntegrityViolationException: could not execute statement [NULL not allowed for column "STOCK_TICKER_ID"; SQL statement:
insert into market_data (adj_close,adj_open,adj_volume,close,dividend,high,low,open,split_factor,stock_ticker_id,trade_date,volume,id) values (?,?,?,?,?,?,?,?,?,?,?,?,?) [23502-214]] [insert into market_data (adj_close,adj_open,adj_volume,close,dividend,high,low,open,split_factor,stock_ticker_id,trade_date,volume,id) values (?,?,?,?,?,?,?,?,?,?,?,?,?)]; SQL [insert into market_data (adj_close,adj_open,adj_volume,close,dividend,high,low,open,split_factor,stock_ticker_id,trade_date,volume,id) values (?,?,?,?,?,?,?,?,?,?,?,?,?)]; constraint [null]))

is having the DTO class mirroring the relationship, dependencies of its corresponding entity class valid??
when DTO classes are supposed to be independent??