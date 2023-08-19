# oppenheimer

A simple stock application that supports CRUD operations 

features.
1. registers stocks from nasdaq and kospi 
2. show historical daily/weekly/monthly prices/volume/high/low/open/close
3. apply indicators on the registered stocks
4. collation of indicators added
5. applies heuristic that uses these indicators as variables


heuristic.
 - dependent on the
1. scale(monthly,weekly/daily) 
2. number of overlapping indicators at a given price point that suggest strong resistance/support

returns
- information of important price points to consider.


reason for creation
- because i am lazy to look at my stock app all the time
- because i am tired to working out what are the important price points according to the indicators added

한국어 ver

기능
1. 나스닥과 한국거래소에 존재하는 주식 등록
2. 과거 일/주/월봉 데이터를 지님
3. 등록된 주식에 지표 적용
4. 지표 데이터 취합 후
5. 각 지표 데이터를 변수로 사용하는 휴리스틱을 적용.

휴리스틱 추가 변수
1. 지표가 어떤 데이터를 통해 생겼는지(월간/주간/일간)
2. 주어진 가격대에서 몇 개의 지표가 강한 지지/저항을 뜻하는지


P.S - because i use the free version of TradingView which already has these features