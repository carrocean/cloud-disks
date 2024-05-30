package com.example.hadoop.dao.basedao;

import com.example.hadoop.conn.HbaseConn;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository("hbaseDao")
public class HbaseDao {

	/**
	 * 创建表
	 * @category create 'tableName','family1','family2','family3'
	 * @param tableName
	 * @param family
	 * @throws Exception
	 */
	public static void createTable(String tableName, String[] family) throws IOException {
		Admin admin = HbaseConn.getConn().getAdmin();

		TableName tn = TableName.valueOf(tableName);
		HTableDescriptor desc = new HTableDescriptor(tn);
		for (int i = 0; i < family.length; i++) {
			desc.addFamily(new HColumnDescriptor(family[i]));
		}
		if (admin.tableExists(tn)) {
			System.out.println("createTable => table Exists!");
			admin.disableTable(tn);
			admin.deleteTable(tn);
			admin.createTable(desc);
		} else {
			admin.createTable(desc);
			System.out.println("createTable => create Success! => " + tn);
		}
	}

    /**
     * 插入或修改一条数据，针对列族中有一个列，value为long类型
     * @category put 'tableName','rowKey','familyName:columnName'
     * @param tableName
     * @param rowKey
     * @param family
     * @param column
     * @param value
     * @return boolean
     * @throws IOException
     */
    public void updateOneData(String tableName, String rowKey, String family, String column, long value) {
		try {
			Table table = HbaseConn.getConn().getTable(TableName.valueOf(tableName));
			Put put = new Put(Bytes.toBytes(rowKey));
	    	put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
	        table.put(put);
	        table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 插入或修改一条数据，针对列族中有一个列，value为String类型
     * @category put 'tableName','rowKey','familyName:columnName'
     * @param tableName
     * @param rowKey
     * @param family
     * @param column
     * @param value
     * @return boolean
     * @throws IOException
     */
    public void updateOneData(String tableName, String rowKey, String family, String column, String value) {
		try {
			Table table = HbaseConn.getConn().getTable(TableName.valueOf(tableName));
			Put put = new Put(Bytes.toBytes(rowKey));
	    	put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
	        table.put(put);
	        table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 插入或修改一条数据，针对列族中有一个列，value为boolean类型
     * @category put 'tableName','rowKey','familyName:columnName'
     * @param tableName
     * @param rowKey
     * @param family
     * @param column
     * @param value
     * @return boolean
     * @throws IOException
     */
    public void updateOneData(String tableName, long rowKey, String family, String column, boolean value) {
		try {
			Table table = HbaseConn.getConn().getTable(TableName.valueOf(tableName));
			Put put = new Put(Bytes.toBytes(rowKey));
	    	put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
	        table.put(put);
	        table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 插入或修改一条数据，针对列族中有一个列，value为boolean类型
     * @category put 'tableName','rowKey','familyName:columnName'
     * @param tableName
     * @param rowKey
     * @param family
     * @param column
     * @param value
     * @return boolean
     * @throws IOException
     */
    public void updateOneData(String tableName, long rowKey, String family, long column, String value) {
		try {
			Table table = HbaseConn.getConn().getTable(TableName.valueOf(tableName));
			Put put = new Put(Bytes.toBytes(rowKey));
	    	put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
	        table.put(put);
	        table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 插入或修改一条数据，针对列族中有一个列，value为long类型
     * @category put 'tableName','rowKey','familyName:columnName'
     * @param tableName
     * @param rowKey
     * @param family
     * @param column
     * @param value
     * @return boolean
     * @throws IOException
     */
    public void updateOneData(String tableName, long rowKey, String family, String column, long value) {
		try {
			Table table = HbaseConn.getConn().getTable(TableName.valueOf(tableName));
			Put put = new Put(Bytes.toBytes(rowKey));
	    	put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
	        table.put(put);
	        table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 插入或修改一条数据，针对列族中有一个列，value为string类型
     * @category put 'tableName','rowKey','familyName:columnName'
     * @param tableName
     * @param rowKey
     * @param family
     * @param column
     * @param value
     * @return boolean
     * @throws IOException
     */
    public void updateOneData(String tableName, long rowKey, String family, String column, String value) {
		try {
			Table table = HbaseConn.getConn().getTable(TableName.valueOf(tableName));
			Put put = new Put(Bytes.toBytes(rowKey));
	    	put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
	        table.put(put);
	        table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 一次性插入或修改多条数据,针对列族中有多个列
     * @category put 'tableName','rowKey','familyName:columnName'
     * @param tableName
     * @param rowKey
     * @param family
     * @param column
     * @param value
     * @return boolean
     * @throws IOException
     */
    public void updateMoreData(String tableName, long rowKey,  String family, String[] column, String[] value) {
    	try {
	        Table table = HbaseConn.getConn().getTable(TableName.valueOf(tableName));
	        Put put = new Put(Bytes.toBytes(rowKey));
	        for (int i = 0; i < column.length; i++) {
	    		put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column[i]), Bytes.toBytes(value[i]));
	        }
	        table.put(put);
	        table.close();
	    } catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 获得一行数据，行健为long类型
     * @category get 'tableName','rowKey'
     * @param tableName
     * @param rowKey
     * @return Result||null
     * @throws IOException
     */
    public Result getResultByRow(String tableName, long rowKey) {
    	Result result = null;
    	try {
	        Table table = HbaseConn.getConn().getTable(TableName.valueOf(tableName));
	        Get get = new Get(Bytes.toBytes(rowKey));
	        result = table.get(get);
	        table.close();
    	} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
    }

    /**
     * 获得一行数据，行健为字符串类型
     * @category get 'tableName','rowKey'
     * @param tableName
     * @param rowKey
     * @return Result||null
     * @throws IOException
     */
    public Result getResultByRow(String tableName, String rowKey) {
    	Result result = null;
    	try {
	        Table table = HbaseConn.getConn().getTable(TableName.valueOf(tableName));
	        Get get = new Get(Bytes.toBytes(rowKey));
	        result = table.get(get);
	        table.close();
    	} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
    }

    /**
     * 按照一定规则扫描表（或者无规则）
     * @param tableName
     * @param filter
     * @return
     */
    public ResultScanner getResultScannerByFilter(String tableName, Filter filter) {
    	ResultScanner resultScanner = null;
    	try {
    		Table table = HbaseConn.getConn().getTable(TableName.valueOf(tableName));
    		Scan scan = new Scan();
			if (filter != null) {
				scan.setFilter(filter);
			}
    		resultScanner = table.getScanner(scan);
    		table.close();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	return resultScanner;
    }

    /**
     * 删除某一行的数据，行健为String类型
     * @category deleteall 'tableName','rowKey'
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public void deleteDataByRow(String tableName, String rowKey) {
    	try {
	        Table table = HbaseConn.getConn().getTable(TableName.valueOf(tableName));
	        Delete deleteAll = new Delete(Bytes.toBytes(rowKey));
	        table.delete(deleteAll);
	        table.close();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 删除某一行的数据，行健为long类型
     * @category deleteall 'tableName','rowKey'
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public void deleteDataByRow(String tableName, long rowKey) {
    	try {
	        Table table = HbaseConn.getConn().getTable(TableName.valueOf(tableName));
	        Delete deleteAll = new Delete(Bytes.toBytes(rowKey));
	        table.delete(deleteAll);
	        table.close();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 删除某一行中某一列的数据，行健为long类型，列名为long类型
     * @category delete 'tableName','rowKey','falilyName:columnName'
     * @param tableName
     * @param rowKey
     * @param falilyName
     * @param columnName
     */
    public void deleteDataByColumn(String tableName, long rowKey, String falilyName, long columnName) {
    	try {
	        Table table = HbaseConn.getConn().getTable(TableName.valueOf(tableName));
	        Delete deleteColumn = new Delete(Bytes.toBytes(rowKey));
	        deleteColumn.addColumns(Bytes.toBytes(falilyName), Bytes.toBytes(columnName));
	        table.delete(deleteColumn);
	        table.close();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
	public long incrCounter(String tableName, String rowKey,  String family, String column, long range) {
		long count = 0l;
		try {
			Table table = HbaseConn.getConn().getTable(TableName.valueOf(tableName));
			count = table.incrementColumnValue(Bytes.toBytes(rowKey), Bytes.toBytes(family), Bytes.toBytes(column), range);
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}
}
