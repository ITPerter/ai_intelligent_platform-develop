import React from 'react';
import { Table } from 'antd';
import PropTypes from 'prop-types';
class StandardTable extends React.Component {
  constructor(props) {
    super(props);
  }
  static getDerivedStateFromProps(nextProps, prevState) {
    const { selectedRowKeys } = nextProps;
    console.log(
      'getDerivedStateFromProps selectedRowKeys is ',
      selectedRowKeys
    );
    if (selectedRowKeys && selectedRowKeys.length > 0) {
      return {
        ...prevState,
        rowSelection: {
          ...prevState.rowSelection,
          selectedRowKeys,
        },
      };
    }
    return null;
  }
  state = {
    rowSelection: {
      selectedRowKeys: [],
      onSelect: function (record, selected, selectedRows, nativeEvent) {},
      onSelectAll: function (selected, selectedRows, changeRows) {},
      onChange: (selectedRowKeys, selectedRows) => {
        this.state.rowSelection.selectedRowKeys = selectedRowKeys;
      },
    },
  };
  getSelectedRowKeys() {
    const {
      rowSelection: { selectedRowKeys },
    } = this.state;
    return selectedRowKeys;
  }
  handleTableChange = (pagination, filters, sorter) => {
    const { onTableChange } = this.props;
    onTableChange && onTableChange(pagination, filters, sorter);
  };
  render() {
    const { rowSelection } = this.state;
    const {
      columns,
      dataSource,
      isLoading,
      rowKey,
      needSelect,
      pagination,
    } = this.props;
    return (
      <Table
        columns={columns}
        dataSource={dataSource}
        rowSelection={needSelect ? rowSelection : null}
        pagination={pagination}
        loading={isLoading}
        rowKey={rowKey}
        onChange={this.handleTableChange}
      ></Table>
    );
  }
}
StandardTable.propTypes = {
  columns: PropTypes.array,
  dataSource: PropTypes.array,
  selectedRowKeys: PropTypes.array, //已选中的
  isLoading: PropTypes.bool,
  rowKey: PropTypes.string,
  needSelect: PropTypes.bool,
  onTableChange: PropTypes.func,
};
StandardTable.defaultProps = {
  columns: null,
  dataSource: null,
  isLoading: false,
  rowKey: 'id',
  needSelect: false,
  onTableChange: () => null,
};
export default StandardTable;
