INSERT INTO engine(id, brand, volume)
VALUES ('11d87faa-7677-48a9-8f6f-a5f432503d8e', 'KIA', '1598');
INSERT INTO invoice(id, created)
VALUES ('0ccd6c07-951f-4dc4-b602-447f615b2718', '2022-09-10T00:07:13.126482');
INSERT INTO auto (id, count, date, manufacturer, model, price, bodytype, engine_id, invoice_id)
VALUES ('010af0f6-99da-49fa-a7cf-1968776b5cec', '18', '2022-09-09 23:51:49.031', '1', 'Model-FlyWay', '545.31',
        'FlyWayBodyType', '11d87faa-7677-48a9-8f6f-a5f432503d8e', '0ccd6c07-951f-4dc4-b602-447f615b2718');
INSERT INTO auto (id, count, date, manufacturer, model, price, bodytype, engine_id, invoice_id)
VALUES ('8d165e2f-728a-45e0-a6d9-2c8d3a989095', '10', '2022-09-09 23:51:49.031', '1', 'Model-FlyWay', '225.65',
        'FlyWayBodyType', '11d87faa-7677-48a9-8f6f-a5f432503d8e', '0ccd6c07-951f-4dc4-b602-447f615b2718');