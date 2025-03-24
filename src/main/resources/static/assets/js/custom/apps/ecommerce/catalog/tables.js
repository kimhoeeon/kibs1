"use strict";

// Class definition kt_exhibitor_table
let KTAppExhibitorMng = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[1, 'desc']],
            'columnDefs': [
                { orderable: false, targets: 0 }, // Disable ordering on column 0 (checkbox)
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 0,
                    'render': function (data, type, row) { return renderCheckBoxCell(data, type, row); }
                },
                {
                    'targets': 3,
                    'render': function (data, type, row) { return renderBadgeCell(data, type, row); }
                },
                {
                    'targets': 4,
                    'render': function (data, type, row) { return renderBoothTypeCell(data, type, row); }
                },
                {
                    'targets': 7,
                    'render': function (data, type, row) { return renderCompanyNameCell(data, type, row); }
                },
                {
                    'targets': 9,
                    'render': function (data, type, row) { return renderPrcTotalCell(data, type, row); }
                },
                {
                    'targets': 10,
                    'render': function (data, type, row) { return renderDepositCell(data, type, row); }
                },
                {
                    'targets': 11,
                    'render': function (data, type, row) { return renderBalanceCell(data, type, row); }
                },
                {
                    'targets': 12,
                    'render': function (data, type, row) { return renderPrcYnCell(data, type, row); }
                },
                {
                    'targets': 14,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [2,5,6] }
            ],
            columns: [
                { data: '' },
                { data: 'rownum' }, //순번
                { data: 'seq'}, //SEQ
                { data: 'approvalStatus' }, //승인여부
                { data: 'boothType' }, //부스
                { data: 'companyNameKo' }, //회사명(국문)
                { data: 'companyNameEn' }, //회사명(영문)
                { data: 'companyName' }, //회사명
                { data: 'id'},
                { data: 'prcTotal' }, //총액
                { data: 'deposit' }, //선금
                { data: 'balance' }, //잔액
                { data: 'prcYn' }, //입금여부
                { data: 'finalRegiDttm' }, //수정일
                { data: 'actions' }
            ]
        });
    }

    function renderCheckBoxCell(data, type, row){
        let renderHTML = '<div class="exhibitor_check form-check form-check-sm form-check-custom form-check-solid">' +
            '<input class="form-check-input" type="checkbox" value="'+ row.seq +'" data-value="' + row.companyNameKo  + ' / ' + row.companyNameEn + '"/>' +
            '</div>';
        return renderHTML;
    }

    function renderBoothTypeCell(data, type, row){
        let renderHTML = row.boothType;
        if(nvl(renderHTML,'') !== ''){
            renderHTML = renderHTML.toString().replaceAll(',','<br>');
        }else{
            renderHTML = '-';
        }
        return renderHTML;
    }

    function renderPrcYnCell(data, type, row){
        let renderHTML = '';
        let prcYn = row.prcYn;
        switch (prcYn){
            case '0':
                renderHTML = '미납';
                break;
            case '1':
                renderHTML = '참가비 납부';
                break;
            case '2':
                renderHTML = '50% 납부';
                break;
            case '3':
                renderHTML = '전액 납부';
                break;
            case '4':
                renderHTML = '완납(부대시설비)';
                break;
            default:
                break;
        }
        return renderHTML;
    }

    function renderBalanceCell(data, type, row){
        let renderHTML = Number(row.balance);
        return renderHTML.toLocaleString();
    }

    function renderDepositCell(data, type, row){
        let renderHTML = Number(row.deposit);
        return renderHTML.toLocaleString();
    }

    function renderPrcTotalCell(data, type, row){
        let renderHTML = Number(row.prcTotal);
        return renderHTML.toLocaleString();
    }

    function renderBadgeCell(data, type, row){
        let approvalStatus = row.approvalStatus;
        let renderHTML = '';
        switch (approvalStatus){
            case '작성중':
            case '승인요청':
            case '참가승인':
                renderHTML += '<div class="badge badge-light-primary fw-bold">';
                break;
            default:
                renderHTML += '<div class="badge badge-light-danger fw-bold">';
                break;
        }
        renderHTML += approvalStatus;
        renderHTML += '</div>';
        return renderHTML;
    }

    function renderCompanyNameCell(data, type, row){
        let companyNameKo = row.companyNameKo;
        let companyNameEn = row.companyNameEn;
        let renderHTML = '<span class="fw-bold">';
        renderHTML += '<a href="/mng/exhibitor/participant/company/detail.do?seq=' + row.seq + '"';
        renderHTML += 'class="text-gray-800 text-hover-primary fs-5 fw-bold text-decoration-none">';
        if(nvl(companyNameEn,'') !== ''){
            renderHTML += companyNameKo + '<br>' + companyNameEn;
        }else{
            renderHTML += companyNameKo;
        }
        renderHTML += '</a>';
        renderHTML += '</span>';
        return renderHTML;
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">상세/수정</a>';
        renderHTML += '</div>';
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_detail_modal_set(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3" data-bs-toggle="modal" data-bs-target="#kt_modal_modify_history">수정이력</a>';
        renderHTML += '</div>';*/
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_select_login(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">이 업체로 로그인</a>';
        renderHTML += '</div>';*/
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_payment_info(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">결제정보</a>';
        renderHTML += '</div>';*/
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_remove(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_exhibitor_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_exhibitor_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 1, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_exhibitor_search();
        }
    };
}();

let KTAppVisitorMng = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : true,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 3,
                    'render': function (data, type, row) { return renderJoinYnCell(data, type, row); }
                },
                {
                    'targets': 4,
                    'render': function (data, type, row) { return renderPartGbnCell(data, type, row); }
                },
                {
                    'targets': 6,
                    'render': function (data, type, row) { return renderEmailCell(data, type, row); }
                },
                {
                    'targets': 11,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1,7] }
            ],
            columns: [
                { data: 'rownum' }, //순번
                { data: 'seq'}, //seq
                { data: 'visitorGbn' }, //구분
                { data: 'joinYn' }, //참석여부
                { data: 'partGbn' }, //참관구분
                { data: 'name' }, //이름
                { data: 'email' }, //이메일
                { data: 'domain' }, //도메인
                { data: 'phone' }, //휴대전화
                { data: 'initRegiDttm' }, //등록일시
                { data: 'finalRegiDttm' }, //수정일시
                { data: 'actions' }
            ]
        });
    }

    function renderPartGbnCell(data, type, row){
        let partGbn = row.partGbn;
        if(nvl(partGbn,'') === ''){
            partGbn = '-';
        }
        return partGbn;
    }

    function renderJoinYnCell(data, type, row){
        let joinYn = row.joinYn;
        let renderHTML = '';
        if(joinYn === "Y"){
            renderHTML = '참석확인';
        }else if(joinYn === "N"){
            renderHTML = '참석취소';
        }else if(joinYn === "R"){
            renderHTML = '삭제';
        }
        return renderHTML;
    }

    function renderEmailCell(data, type, row){
        let email = row.email;
        let domain = row.domain;
        let renderHTML = email + '@' + domain;
        return renderHTML;
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_participant_visitor_modify_init_set(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">상세/수정</a>';
        renderHTML += '</div>';
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">참석확인</a>';
        renderHTML += '</div>';*/
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_participant_visitor_remove(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_participant_visitor_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_participant_visitor_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_participant_visitor_search();
        }
    };
}();

let KTAppMemberMng = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 3,
                    'render': function (data, type, row) { return renderBadgeCell(data, type, row); }
                },
                {
                    'targets': 4,
                    'render': function (data, type, row) { return renderIdCell(data, type, row); }
                },
                {
                    'targets': 6,
                    'render': function (data, type, row) { return renderCompanyNameCell(data, type, row); }
                },
                {
                    'targets': 9,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' }, //순번
                { data: 'seq'}, //SEQ
                { data: 'gbn' }, //유형
                { data: 'stat' }, //상태
                { data: 'id' }, //아이디
                { data: 'nameKo' }, //이름/회사(국문)
                { data: 'nameEn' }, //이름/회사(영문)
                { data: 'email' }, //이메일주소
                { data: 'initRegiDttm' }, //가입일시
                { data: 'actions' }
            ]
        });
    }

    function renderIdCell(data, type, row){
        let id = row.id;
        if(nvl(id,'') === ''){
            id = '-';
        }
        return id;
    }

    function renderBadgeCell(data, type, row){
        let stat = row.stat;
        let renderHTML = '';
        switch (stat){
            case '작성중':
            case '승인요청':
            case '참가승인':
            case 'Y':
                renderHTML += '<div class="badge badge-light-primary fw-bold">';
                renderHTML += '회원';
                break;
            default:
                renderHTML += '<div class="badge badge-light-danger fw-bold">';
                renderHTML += '비회원';
                break;
        }
        renderHTML += '</div>';
        return renderHTML;
    }

    function renderCompanyNameCell(data, type, row){
        let nameEn = row.nameEn;
        let renderHTML = '';
        /*'<span class="fw-bold">';
        renderHTML += '<a href="/mng/exhibitor/participant/company/detail.do?seq=' + row.seq + '"';
        renderHTML += 'class="text-gray-800 text-hover-primary fs-5 fw-bold text-decoration-none">';*/
        if(nvl(nameEn,'').toString().trim() !== ''){
            renderHTML = nameEn;
        }else{
            renderHTML = '-';
        }
        /*renderHTML += '</a>';
        renderHTML += '</span>';*/
        return renderHTML;
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_member_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">상세보기</a>';
        renderHTML += '</div>';
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_detail_modal_set(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3" data-bs-toggle="modal" data-bs-target="#kt_modal_modify_history">수정이력</a>';
        renderHTML += '</div>';*/
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_select_login(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">이 업체로 로그인</a>';
        renderHTML += '</div>';*/
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_payment_info(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">결제정보</a>';
        renderHTML += '</div>';*/
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_remove(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';*/
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_member_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_member_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_member_search();
        }
    };
}();

let KTAppExhibitorTransferCompany = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 5,
                    'render': function (data, type, row) { return renderCompanyNameCell(data, type, row); }
                },
                {
                    'targets': 9,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1,3,4] }
            ],
            columns: [
                { data: 'rownum' }, //순번
                { data: 'seq'}, //SEQ
                { data: 'transferYear' }, //연도
                { data: 'companyNameKo' }, //회사명(국문)
                { data: 'companyNameEn' }, //회사명(영문)
                { data: 'companyName' }, //회사명
                { data: 'id' }, //아이디(사업자등록번호)
                { data: 'initRegiDttm' }, //등록일
                { data: 'finalRegiDttm' }, //수정일
                { data: 'actions' }
            ]
        });
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_transfer_company_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">상세/수정</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    function renderCompanyNameCell(data, type, row){
        let companyNameKo = row.companyNameKo;
        let companyNameEn = row.companyNameEn;
        let renderHTML = '<span class="fw-bold">';
        renderHTML += '<a href="/mng/exhibitor/transfer/company/detail.do?seq=' + row.seq + '"';
        renderHTML += 'class="text-gray-800 text-hover-primary fs-5 fw-bold">';
        renderHTML += companyNameKo + '<br>' + companyNameEn;
        renderHTML += '</a>';
        renderHTML += '</span>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_exhibitor_transfer_company_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_exhibitor_transfer_company_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_transfer_company_search();
        }
    };
}();

let KTAppExhibitorTransferVisitor = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : true,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 4,
                    'render': function (data, type, row) { return renderJoinYnCell(data, type, row); }
                },
                {
                    'targets': 5,
                    'render': function (data, type, row) { return renderNameCell(data, type, row); }
                },
                {
                    'targets': 6,
                    'render': function (data, type, row) { return renderTelCell(data, type, row); }
                },
                {
                    'targets': 7,
                    'render': function (data, type, row) { return renderEmailCell(data, type, row); }
                },
                {
                    'targets': 12,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1,8] }
            ],
            columns: [
                { data: 'rownum' }, //순번
                { data: 'seq' }, //seq
                { data: 'joinYear' }, //연도
                { data: 'visitorGbn' }, //구분
                { data: 'joinYn' }, //참석여부
                { data: 'name' }, //이름
                { data: 'phone' }, //전화번호/휴대전화
                { data: 'email' }, //이메일
                { data: 'domain' }, //도메인
                { data: 'partnerYn' }, //동반자여부
                { data: 'initRegiDttm' }, //등록일시
                { data: 'finalRegiDttm' }, //수정일시
                { data: 'actions' }
            ]
        });
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_transfer_visitor_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">상세/수정</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    function renderNameCell(data, type, row){
        let rowSeq = row.seq;
        let name = row.name;
        return '<a onClick="f_transfer_visitor_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3" style="cursor: pointer; font-weight: bold;">' + name + '</a>';
    }

    function renderEmailCell(data, type, row){
        let email = row.email;
        let domain = row.domain;
        return email + '@' + domain;
    }

    function renderJoinYnCell(data, type, row){
        let joinYn = row.joinYn;
        let renderHTML = '';
        if(joinYn === "Y"){
            renderHTML = '참석확인';
        }else if(joinYn === "N"){
            renderHTML = '참석취소';
        }else if(joinYn === "R"){
            renderHTML = '삭제';
        }
        return renderHTML;
    }

    function renderTelCell(data, type, row){
        let tel = row.tel;
        if(nvl(tel,'') === ''){
            tel = '-';
        }
        let phone = row.phone;
        return tel + '<br>' + phone;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_exhibitor_transfer_visitor_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_exhibitor_transfer_visitor_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_transfer_visitor_search();
        }
    };
}();

let KTAppExhibitorApplicationBooth = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 2,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderCompanyNameCell(data, type, row); }
                },
                {
                    'targets': 4,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderBoothTypeCell(data, type, row); }
                },
                {
                    'targets': 5,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderBoothCntCell(data, type, row); }
                },
                {
                    'targets': 6,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderBoothPrcCell(data, type, row); }
                },
                {
                    'targets': 7,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderDiscountTypeCell(data, type, row); }
                },
                {
                    'targets': 8,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderDiscountPrcCell(data, type, row); }
                },
                {
                    'targets': 9,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderBoothPrcSumCell(data, type, row); }
                },
                {
                    'targets': 12,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1,3,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29] }
            ],
            columns: [
                { data: 'rownum' }, //순번
                { data: 'seq'}, //seq
                { data: 'companyNameKo' }, //회사명(국문)
                { data: 'companyNameEn' }, //회사명(영문)
                { data: 'boothType' }, //부스타입
                { data: 'boothCnt' }, //부스수량
                { data: 'boothPrc' }, //부스가격
                { data: 'discountType' }, //할인타입
                { data: 'discountPrc' }, //할인가격
                { data: 'boothPrcSum' }, //부스신청 총액
                { data: 'initRegiDttm' }, //등록일시
                { data: 'finalRegiDttm' }, //수정일시
                { data: 'actions' },
                { data: 'registrationCnt' }, //등록비 수량
                { data: 'registrationFee' }, //등록비 금액
                { data: 'standAloneBoothCnt' }, //독립부스 수량
                { data: 'standAloneBoothFee' }, //독립부스 금액
                { data: 'assemblyBoothCnt' }, //조립부스 수량
                { data: 'assemblyBoothFee' }, //조립부스 금액
                { data: 'onlineBoothCnt' }, //온라인부스 수량
                { data: 'onlineBoothFee' }, //온라인부스 금액
                { data: 'discountEarly1' }, //1차 조기신청
                { data: 'discountEarly2' }, //2차 조기신청
                { data: 'discountRe1' }, //재참가할인1
                { data: 'discountRe2' }, //재참가할인2
                { data: 'discountRe3' }, //재참가할인3
                { data: 'discountScale1' }, //규모할인1
                { data: 'discountScale2' }, //규모할인2
                { data: 'discountScale3' }, //규모할인3
                { data: 'discountFirst' } //첫 참가할인
            ]
        });
    }

    function renderBoothPrcSumCell(data, type, row){
        let renderHTML = Number(row.boothPrcSum);
        return renderHTML.toLocaleString();
    }

    function renderDiscountPrcCell(data, type, row){
        return ((parseInt(row.registrationFee) + parseInt(row.standAloneBoothFee) + parseInt(row.assemblyBoothFee) + parseInt(row.onlineBoothFee))
            - parseInt(row.boothPrcSum)).toLocaleString();
    }

    function renderDiscountTypeCell(data, type, row){
        let renderHTML = '';
        if(row.discountType != null && row.discountType !== ""){
            let discountType_arr = row.discountType.split(',');
            for(let i=0; i<discountType_arr.length; i++){
                renderHTML += discountType_arr[i];
                renderHTML += '<br>';
            }
            if(renderHTML.substring(renderHTML.length-4) === '<br>'){
                renderHTML = renderHTML.substring(0,renderHTML.length-4);
            }
        }else{
            renderHTML += '-';
        }
        return renderHTML;
    }

    function renderBoothPrcCell(data, type, row){
        return (parseInt(row.registrationFee) + parseInt(row.standAloneBoothFee) + parseInt(row.assemblyBoothFee) + parseInt(row.onlineBoothFee)).toLocaleString();
    }

    function renderBoothCntCell(data, type, row){
        return (parseInt(row.registrationCnt) + parseInt(row.standAloneBoothCnt) + parseInt(row.assemblyBoothCnt) + parseInt(row.onlineBoothCnt)).toString();
    }

    function renderBoothTypeCell(data, type, row){
        let renderHTML = '';
        if(row.boothType != null) {
            let boothType_arr = row.boothType.split(',');
            for (let i = 0; i < boothType_arr.length; i++) {
                let booth = boothType_arr[i];
                if (booth === '등록비') {
                    renderHTML += booth + ' (' + row.registrationCnt + ')';
                    renderHTML += '<br>';
                } else if (booth === '독립부스') {
                    renderHTML += booth + ' (' + row.standAloneBoothCnt + ')';
                    renderHTML += '<br>';
                } else if (booth === '조립부스') {
                    renderHTML += booth + ' (' + row.assemblyBoothCnt + ')';
                    renderHTML += '<br>';
                } else if (booth === '온라인부스') {
                    renderHTML += booth + ' (' + row.onlineBoothCnt + ')';
                }
            }
        }
        return renderHTML;
    }

    function renderCompanyNameCell(data, type, row){
        let companyNameKo = row.companyNameKo;
        let companyNameEn = row.companyNameEn;
        let renderHTML = '<span class="fw-bold">';
        renderHTML += '<a href="/mng/exhibitor/application/booth/detail.do?seq=' + row.seq + '"';
        renderHTML += 'class="text-gray-800 text-hover-primary fs-5 fw-bold">';
        renderHTML += companyNameKo + '<br>' + companyNameEn;
        renderHTML += '</a>';
        renderHTML += '</span>';
        return renderHTML;
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_application_booth_modify_init_set(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">상세/수정</a>';
        renderHTML += '</div>';
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">참석확인</a>';
        renderHTML += '</div>';*/
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_exhibitor_application_booth_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_exhibitor_application_booth_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_application_booth_search();
        }
    };
}();

let KTAppExhibitorApplicationSign = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 2,
                    'render': function (data, type, row) { return renderCompanyNameCell(data, type, row); }
                },
                {
                    'targets': 8,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1,3] }
            ],
            columns: [
                { data: 'rownum' }, //순번
                { data: 'seq'}, //seq
                { data: 'companyNameKo' }, //회사명(국문)
                { data: 'companyNameEn' }, //회사명(영문)
                { data: 'companySignNameKo' }, //상호간판명(국문)
                { data: 'companySignNameEn' }, //상호간판명(영문)
                { data: 'initRegiDttm' }, //등록일시
                { data: 'finalRegiDttm' }, //수정일시
                { data: 'actions' }
            ]
        });
    }

    function renderCompanyNameCell(data, type, row){
        let companyNameKo = row.companyNameKo;
        let companyNameEn = row.companyNameEn;
        let renderHTML = companyNameKo + '<br>' + companyNameEn;
        return renderHTML;
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_application_sign_modify_init_set(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">상세/수정</a>';
        renderHTML += '</div>';
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">참석확인</a>';
        renderHTML += '</div>';*/
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_exhibitor_application_sign_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_exhibitor_application_sign_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_application_sign_search();
        }
    };
}();

let KTAppExhibitorApplicationUtility = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 2,
                    'render': function (data, type, row) { return renderCompanyNameCell(data, type, row); }
                },
                {
                    'targets': 20,
                    'render': function (data, type, row) { return renderUtilityGbnCell(data, type, row); }
                },
                {
                    'targets': 21,
                    'render': function (data, type, row) { return renderUtilityPrcSumCell(data, type, row); }
                },
                {
                    'targets': 24,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19] }
            ],
            columns: [
                { data: 'rownum' }, //순번
                { data: 'seq'}, //seq
                { data: 'companyNameKo' }, //회사명(국문)
                { data: 'companyNameEn' }, //회사명(영문)
                { data: 'utilityJuganCnt' },
                { data: 'utilityJuganFee' },
                { data: 'utilityDayCnt' },
                { data: 'utilityDayFee' },
                { data: 'utilityCompressedAirCnt' },
                { data: 'utilityCompressedAirFee' },
                { data: 'utilityWaterBasicCnt' },
                { data: 'utilityWaterBasicFee' },
                { data: 'utilityInternetCnt' },
                { data: 'utilityInternetFee' },
                { data: 'utilityPytexNewCnt' },
                { data: 'utilityPytexNewFee' },
                { data: 'utilityPytexReCnt' },
                { data: 'utilityPytexReFee' },
                { data: 'utilityBarcodeCnt' },
                { data: 'utilityBarcodeFee' },
                { data: 'utilityGbn' }, //구분
                { data: 'utilityPrcSum' }, //총액
                { data: 'initRegiDttm' }, //등록일시
                { data: 'finalRegiDttm' }, //수정일시
                { data: 'actions' }
            ]
        });
    }

    function renderCompanyNameCell(data, type, row){
        let companyNameKo = row.companyNameKo;
        let companyNameEn = row.companyNameEn;
        let renderHTML = companyNameKo + '<br>' + companyNameEn;
        return renderHTML;
    }

    function renderUtilityPrcSumCell(data, type, row){
        let renderHTML = Number(row.utilityPrcSum);
        return renderHTML.toLocaleString();
    }

    function renderUtilityGbnCell(data, type, row){
        let utilityType = '';

        let utilityJuganCnt = row.utilityJuganCnt;
        let utilityDayCnt = row.utilityDayCnt;
        let utilityCompressedAirCnt = row.utilityCompressedAirCnt;
        let utilityWaterBasicCnt = row.utilityWaterBasicCnt;
        let utilityInternetCnt = row.utilityInternetCnt;
        let utilityPytexNewCnt = row.utilityPytexNewCnt;
        let utilityPytexReCnt = row.utilityPytexReCnt;
        let utilityBarcodeCnt = row.utilityBarcodeCnt;

        if(utilityJuganCnt > 0){ utilityType += ('주간 단상 220V' + ' (' + row.utilityJuganCnt + ')' + '<br>'); }
        if(utilityDayCnt > 0){ utilityType += ('24시간용 220V' + ' (' + row.utilityDayCnt + ')' + '<br>'); }
        if(utilityCompressedAirCnt > 0){ utilityType += ('압축공기 기본형' + ' (' + row.utilityCompressedAirCnt + ')' + '<br>'); }
        if(utilityWaterBasicCnt > 0){ utilityType += ('급배수 기본형' + ' (' + row.utilityWaterBasicCnt + ')' + '<br>'); }
        if(utilityInternetCnt > 0){ utilityType += ('인터넷' + ' (' + row.utilityInternetCnt + ')' + '<br>'); }
        if(utilityPytexNewCnt > 0){ utilityType += ('파이텍스(신품)' + ' (' + row.utilityPytexNewCnt + ')' + '<br>'); }
        if(utilityPytexReCnt > 0){ utilityType += ('파이텍스(재사용품)' + ' (' + row.utilityPytexReCnt + ')' + '<br>'); }
        if(utilityBarcodeCnt > 0){ utilityType += ('바코드 리더기' + ' (' + row.utilityBarcodeCnt + ')' + '<br>'); }

        if(utilityType.substring(utilityType.length-4) === '<br>'){
            utilityType = utilityType.substring(0,utilityType.length-4);
        }

        if(nvl(utilityType,"") === ""){
            utilityType = '-';
        }

        return utilityType;
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_application_utility_modify_init_set(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">상세/수정</a>';
        renderHTML += '</div>';
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">참석확인</a>';
        renderHTML += '</div>';*/
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_exhibitor_application_utility_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_exhibitor_application_utility_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_application_utility_search();
        }
    };
}();

let KTAppExhibitorApplicationPass = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 3,
                    'render': function (data, type, row) { return renderCompanyNameCell(data, type, row); }
                },
                {
                    'targets': 5,
                    'render': function (data, type, row) { return renderPassNameCell(data, type, row); }
                },
                {
                    'targets': 6,
                    'render': function (data, type, row) { return renderPassNameEnCell(data, type, row); }
                },
                {
                    'targets': 8,
                    'render': function (data, type, row) { return renderPassPositionKoCell(data, type, row); }
                },
                {
                    'targets': 9,
                    'render': function (data, type, row) { return renderPassPositionEnCell(data, type, row); }
                },
                {
                    'targets': 10,
                    'render': function (data, type, row) { return renderPassNoteCell(data, type, row); }
                },
                {
                    'targets': 11,
                    'render': function (data, type, row) { return renderInitRegiDttmCell(data, type, row); }
                },
                {
                    'targets': 12,
                    'render': function (data, type, row) { return renderFinalRegiDttmCell(data, type, row); }
                },
                {
                    'targets': 13,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1,2,4,7] }
            ],
            columns: [
                { data: 'rownum' }, //순번
                { data: 'seq' }, //업체seq
                { data: 'id' }, //id
                { data: 'companyNameko' }, //회사명(국문)
                { data: 'companyNameEn' }, //회사명(영문)
                { data: 'passName' }, //성명(국문)
                { data: 'passFirstName' }, //이름(영문)
                { data: 'passLastName' }, //성(영문)
                { data: 'passPositionKo' }, //직책(국문)
                { data: 'passPositionEn' }, //직책(영문)
                { data: 'passNote' }, //비고
                { data: 'initRegiDttm' }, //등록일시
                { data: 'finalRegiDttm' }, //수정일시
                { data: 'actions' }
            ]
        });
    }

    function renderFinalRegiDttmCell(data, type, row){
        let finalRegiDttm = row.finalRegiDttm;
        if(nvl(finalRegiDttm,"") === ""){
            finalRegiDttm = '-';
        }
        return finalRegiDttm;
    }

    function renderInitRegiDttmCell(data, type, row){
        let initRegiDttm = row.initRegiDttm;
        if(nvl(initRegiDttm,"") === ""){
            initRegiDttm = '-';
        }
        return initRegiDttm;
    }

    function renderPassNoteCell(data, type, row){
        let passNote = row.passNote;
        if(nvl(passNote,"") === ""){
            passNote = '-';
        }
        return passNote;
    }

    function renderPassPositionEnCell(data, type, row){
        let passPositionEn = row.passPositionEn;
        if(nvl(passPositionEn,'') === ''){
            passPositionEn = '-';
        }
        return passPositionEn;
    }

    function renderPassPositionKoCell(data, type, row){
        let passPositionKo = row.passPositionKo;
        if(nvl(passPositionKo,"") === ""){
            passPositionKo = '-';
        }
        return passPositionKo;
    }

    function renderPassNameCell(data, type, row){
        let passName = row.passName;
        if(nvl(passName,"") === ""){
            passName = '-';
        }
        return passName;
    }

    function renderPassNameEnCell(data, type, row){
        let passFirstName = row.passFirstName;
        let passLastName = row.passLastName;
        let renderHTML = '';
        if(nvl(passFirstName,"") === "" || nvl(passLastName,"") === ""){
            renderHTML = '-';
        }else{
            renderHTML = passFirstName + ' ' + passLastName;
        }

        return renderHTML;
    }

    function renderCompanyNameCell(data, type, row){
        let companyNameKo = row.companyNameKo;
        let companyNameEn = row.companyNameEn;
        let renderHTML = companyNameKo + '<br>' + companyNameEn;
        return renderHTML;
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_application_pass_modify_init_set(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">상세/수정</a>';
        renderHTML += '</div>';
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">참석확인</a>';
        renderHTML += '</div>';*/
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_exhibitor_application_pass_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_exhibitor_application_pass_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_application_pass_search();
        }
    };
}();

let KTAppExhibitorApplicationBuyer = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 3,
                    'render': function (data, type, row) { return renderCompanyNameCell(data, type, row); }
                },
                {
                    'targets': 5,
                    'render': function (data, type, row) { return renderCountryCell(data, type, row); }
                },
                {
                    'targets': 7,
                    'render': function (data, type, row) { return renderBuyerCompanyCell(data, type, row); }
                },
                {
                    'targets': 9,
                    'render': function (data, type, row) { return renderBuyerNameCell(data, type, row); }
                },
                {
                    'targets': 11,
                    'render': function (data, type, row) { return renderEmailCell(data, type, row); }
                },
                {
                    'targets': 12,
                    'render': function (data, type, row) { return renderConnectionCell(data, type, row); }
                },
                {
                    'targets': 15,
                    'render': function (data, type, row) { return renderInitRegiDttmCell(data, type, row); }
                },
                {
                    'targets': 16,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1,2,4,6,8,10,13,14] }
            ],
            columns: [
                { data: 'rownum' }, //순번
                { data: 'seq' }, //업체seq
                { data: 'id' }, //id
                { data: 'companyNameKo' }, //회사명(국문)
                { data: 'companyNameEn' }, //회사명(영문)
                { data: 'buyerCompanyCountry' }, //국가
                { data: 'buyerCompanyLocation' }, //소재지
                { data: 'buyerCompanyName' }, //바이어업체명
                { data: 'buyerCompanyHomepage' }, //바이어홈페이지
                { data: 'buyerCompanyDepart' }, //바이어부서
                { data: 'buyerCompanyPosition' }, //바이어직책
                { data: 'buyerCompanyEmail' }, //바이어이메일
                { data: 'buyerCompanyTel' }, //바이어전화번호
                { data: 'buyerCompanyPhone' }, //바이어휴대전화
                { data: 'buyerCompanyFax' }, //바이어Fax
                { data: 'initRegiDttm' }, //등록일시
                { data: 'actions' }
            ]
        });
    }

    function renderInitRegiDttmCell(data, type, row){
        let initRegiDttm = row.initRegiDttm;
        if(nvl(initRegiDttm,"") === ""){ initRegiDttm = '-'; }
        return initRegiDttm;
    }

    function renderEmailCell(data, type, row){
        let buyerCompanyEmail = row.buyerCompanyEmail;
        if(nvl(buyerCompanyEmail,"") === ""){ buyerCompanyEmail = '-'; }
        return buyerCompanyEmail;
    }

    function renderCompanyNameCell(data, type, row){
        let companyNameKo = row.companyNameKo;
        let companyNameEn = row.companyNameEn;
        return companyNameKo + '<br>' + companyNameEn;
    }

    function renderCountryCell(data, type, row){
        let buyerCompanyCountry = row.buyerCompanyCountry;
        if(nvl(buyerCompanyCountry,"") === ""){ buyerCompanyCountry = '-'; }
        let buyerCompanyLocation = row.buyerCompanyLocation;
        if(nvl(buyerCompanyLocation,"") === ""){ buyerCompanyLocation = '-'; }
        return buyerCompanyCountry + ' / ' + buyerCompanyLocation;
    }

    function renderBuyerCompanyCell(data, type, row){
        let buyerCompanyName = row.buyerCompanyName;
        if(nvl(buyerCompanyName,"") === ""){ buyerCompanyName = '-'; }
        let buyerCompanyHomepage = row.buyerCompanyHomepage;
        if(nvl(buyerCompanyHomepage,"") === ""){ buyerCompanyHomepage = '-'; }
        return buyerCompanyName + '<br>' + '( ' + buyerCompanyHomepage + ' )';
    }

    function renderBuyerNameCell(data, type, row){
        let buyerCompanyDepart = row.buyerCompanyDepart;
        if(nvl(buyerCompanyDepart,"") === ""){ buyerCompanyDepart = '-'; }
        let buyerCompanyPosition = row.buyerCompanyPosition;
        if(nvl(buyerCompanyPosition,"") === ""){ buyerCompanyPosition = '-'; }
        return buyerCompanyDepart + ' / ' + buyerCompanyPosition;
    }

    function renderConnectionCell(data, type, row){
        let buyerCompanyTel = row.buyerCompanyTel;
        if(nvl(buyerCompanyTel,"") === ""){ buyerCompanyTel = '-'; }
        let buyerCompanyPhone = row.buyerCompanyPhone;
        if(nvl(buyerCompanyPhone,"") === ""){ buyerCompanyPhone = '-'; }
        let buyerCompanyFax = row.buyerCompanyFax;
        if(nvl(buyerCompanyFax,"") === ""){ buyerCompanyFax = '-'; }
        let renderHTML = '전화번호: ' + buyerCompanyTel + '<br>';
        renderHTML += '휴대전화: ' + buyerCompanyPhone + '<br>';
        renderHTML += 'FAX: ' + buyerCompanyFax;
        return renderHTML;
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_application_buyer_modify_init_set(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">상세/수정</a>';
        renderHTML += '</div>';
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">참석확인</a>';
        renderHTML += '</div>';*/
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_exhibitor_application_buyer_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_exhibitor_application_buyer_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_application_buyer_search();
        }
    };
}();

let KTAppExhibitorApplicationGift = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'asc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 3,
                    'render': function (data, type, row) { return renderCompanyNameCell(data, type, row); }
                },
                {
                    'targets': 5,
                    'render': function (data, type, row) { return renderGiftGbnCell(data, type, row); }
                },
                {
                    'targets': 6,
                    'render': function (data, type, row) { return renderGiftCntCell(data, type, row); }
                },
                {
                    'targets': 7,
                    'render': function (data, type, row) { return renderGiftClassifyCell(data, type, row); }
                },
                {
                    'targets': 8,
                    'render': function (data, type, row) { return renderGiftNameCell(data, type, row); }
                },
                {
                    'targets': 9,
                    'render': function (data, type, row) { return renderPriceCell(data, type, row); }
                },
                {
                    'targets': 11,
                    'render': function (data, type, row) { return renderInitRegiDttmCell(data, type, row); }
                },
                {
                    'targets': 12,
                    'render': function (data, type, row) { return renderFinalRegiDttmCell(data, type, row); }
                },
                {
                    'targets': 13,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1,2,4,10] }
            ],
            columns: [
                { data: 'rownum' }, //순번
                { data: 'seq'}, //업체seq
                { data: 'id'}, //seq
                { data: 'companyNameKo' }, //회사명(국문)
                { data: 'companyNameEn' }, //회사명(영문)
                { data: 'giftGbn' }, //구분
                { data: 'giftCnt' }, //수량
                { data: 'giftClassify' }, //분류
                { data: 'giftName' }, //;경품명
                { data: 'giftPrice' }, //소비자가
                { data: 'giftSponsorPrice' }, //협찬가
                { data: 'initRegiDttm' }, //등록일시
                { data: 'finalRegiDttm' }, //수정일시
                { data: 'actions' }
            ]
        });
    }

    function renderFinalRegiDttmCell(data, type, row){
        let finalRegiDttm = row.finalRegiDttm;
        if(nvl(finalRegiDttm,"") === ""){ finalRegiDttm = '-'; }
        return finalRegiDttm;
    }

    function renderInitRegiDttmCell(data, type, row){
        let initRegiDttm = row.initRegiDttm;
        if(nvl(initRegiDttm,"") === ""){ initRegiDttm = '-'; }
        return initRegiDttm;
    }

    function renderGiftNameCell(data, type, row){
        let giftName = row.giftName;
        if(nvl(giftName,"") === ""){ giftName = '-'; }
        return giftName;
    }

    function renderGiftClassifyCell(data, type, row){
        let giftClassify = row.giftClassify;
        if(nvl(giftClassify,"") === ""){ giftClassify = '-'; }
        return giftClassify;
    }

    function renderGiftCntCell(data, type, row){
        let giftCnt = row.giftCnt;
        if(nvl(giftCnt,"") === ""){ giftCnt = '-'; }
        return giftCnt;
    }

    function renderGiftGbnCell(data, type, row){
        let giftGbn = row.giftGbn;
        if(nvl(giftGbn,"") === ""){ giftGbn = '-'; }
        return giftGbn;
    }

    function renderCompanyNameCell(data, type, row){
        let companyNameKo = row.companyNameKo;
        let companyNameEn = row.companyNameEn;
        return companyNameKo + '<br>' + companyNameEn;
    }

    function renderPriceCell(data, type, row){
        let giftPrice = row.giftPrice;
        if(nvl(giftPrice,"") === ""){ giftPrice = '-'; }else{giftPrice = Number(giftPrice);}
        let giftSponsorPrice = row.giftSponsorPrice;
        if(nvl(giftSponsorPrice,"") === ""){ giftSponsorPrice = '-'; }else{giftSponsorPrice = Number(giftSponsorPrice);}
        return giftPrice.toLocaleString() + ' / ' + giftSponsorPrice.toLocaleString();
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_application_gift_modify_init_set(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">상세/수정</a>';
        renderHTML += '</div>';
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">참석확인</a>';
        renderHTML += '</div>';*/
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_exhibitor_application_gift_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_exhibitor_application_gift_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_application_gift_search();
        }
    };
}();

let KTAppExhibitorApplicationBanner = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'asc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 3,
                    'render': function (data, type, row) { return renderCompanyNameCell(data, type, row); }
                },
                {
                    'targets': 8,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1,2,4] }
            ],
            columns: [
                { data: 'rownum' }, //순번
                { data: 'seq'}, //seq
                { data: 'exhibitorSeq'}, //업체seq
                { data: 'companyNameKo' }, //회사명(국문)
                { data: 'companyNameEn' }, //회사명(영문)
                { data: 'content' }, //내용
                { data: 'initRegiDttm' }, //등록일시
                { data: 'finalRegiDttm' }, //수정일시
                { data: 'actions' }
            ]
        });
    }

    function renderCompanyNameCell(data, type, row){
        let companyNameKo = row.companyNameKo;
        let companyNameEn = row.companyNameEn;
        let renderHTML = companyNameKo + '<br>' + companyNameEn;
        return renderHTML;
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">상세/수정</a>';
        renderHTML += '</div>';
        /*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">참석확인</a>';
        renderHTML += '</div>';*/
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_exhibitor_application_banner_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_exhibitor_application_banner_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            /* 조회 */
            f_application_banner_search();
        }
    };
}();

let KTAppExhibitorApplicationOnline = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': true,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 2,
                    'render': function (data, type, row) { return renderViewYnCell(data, type, row); }
                },
                {
                    'targets': 4,
                    'render': function (data, type, row) { return renderCompanyNameCell(data, type, row); }
                },
                {
                    'targets': 6,
                    'render': function (data, type, row) { return renderFieldPartCell(data, type, row); }
                },
                {
                    'targets': 7,
                    'render': function (data, type, row) { return renderProductOptionSmallCell(data, type, row); }
                },
                {
                    'targets': 10,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1,5] }
            ],
            columns: [
                { data: 'rownum' }, //순번
                { data: 'seq'}, //seq
                { data: 'companyOnlineViewYn'}, //온라인노출여부
                { data: 'transferYear'}, //참가년도
                { data: 'companyNameKo' }, //회사명(국문)
                { data: 'companyNameEn' }, //회사명(영문)
                { data: 'fieldPart' }, //참가분야
                { data: 'productOptionSmall' }, //제품분류(소)
                { data: 'initRegiDttm' }, //등록일시
                { data: 'finalRegiDttm' }, //수정일시
                { data: 'actions' }
            ]
        });
    }

    function renderProductOptionSmallCell(data, type, row){
        let productOptionSmall_full = row.productOptionSmall;
        let renderHTML = '';
        if(nvl(productOptionSmall_full,"") !== ""){
            if(productOptionSmall_full.includes('^')){
                let productOptionSmall = productOptionSmall_full.toString().split('^');
                for(let i=0; i<productOptionSmall.length; i++){
                    renderHTML += '- ' + productOptionSmall[i];
                    if((i+1) !== productOptionSmall.length){
                        renderHTML += '<br>';
                    }
                }
            }else{
                renderHTML = productOptionSmall_full;
            }
        }
        return renderHTML;
    }

    function renderFieldPartCell(data, type, row){
        let fieldPart = row.fieldPart;
        let renderHTML = '';
        if(nvl(fieldPart,"") !== ""){
            renderHTML = fieldPart.toString().substring(0, fieldPart.toString().indexOf(' ('));
        }
        return renderHTML;
    }

    function renderViewYnCell(data, type, row){
        let companyOnlineViewYn = row.companyOnlineViewYn;
        let renderHTML = '';
        if(companyOnlineViewYn === 'Y'){
            renderHTML += '<div class="badge badge-light-primary fw-bold">';
            renderHTML += '노출';
            renderHTML += '</div>';
        }else{
            renderHTML += '<div class="badge badge-light-danger fw-bold">';
            renderHTML += '미노출';
            renderHTML += '</div>';
        }
        return renderHTML;
    }

    function renderCompanyNameCell(data, type, row){
        let companyNameKo = row.companyNameKo;
        let companyNameEn = row.companyNameEn;
        return companyNameKo + '<br>' + companyNameEn;
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        /*let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_application_online_modify_init_set(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3" style="color: #7e8299 !important;">상세/수정</a>';
        renderHTML += '</div>';
        /!*renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_exhibitor_detail(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">참석확인</a>';
        renderHTML += '</div>';*!/
        renderHTML += '</div>';*/
        let renderHTML = '<a onclick="f_application_online_modify_init_set(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3 cursor-pointer text-decoration-none">';
        renderHTML += '<i class="ki-duotone ki-notepad-edit">';
        renderHTML += '<span class="path1"></span>';
        renderHTML += '<span class="path2"></span>';
        renderHTML += '</i>';
        renderHTML += ' 상세보기';
        renderHTML += '</a>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_exhibitor_application_online_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_exhibitor_application_online_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on( 'select', function ( e, dt, type, indexes ) {
                if ( type === 'row' ) {
                    let rowSeq = dataTbl.rows( indexes ).data().pluck( 'seq' )[0];
                    let onlineViewYnBtn = $('#online_view_yn_btn');
                    onlineViewYnBtn.attr('value', rowSeq);
                    let companyOnlineViewYn = dataTbl.rows( indexes ).data().pluck( 'companyOnlineViewYn' )[0];
                    onlineViewYnBtn.attr('data-view', companyOnlineViewYn);
                }
            } );

            dataTbl.on( 'deselect', function ( e, dt, type, indexes ) {
                if ( type === 'row' ) {
                    let onlineViewYnBtn = $('#online_view_yn_btn');
                    onlineViewYnBtn.removeAttr('value');
                    onlineViewYnBtn.removeAttr('data-view');
                }
            } );

            dataTbl.on('order.dt search.dt', function () {
                    let i = dataTbl.rows().count();
                    dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                        .every(function (cell) {
                            this.data(i--);
                        });
                })
                .draw();

            /* 조회 */
            f_application_online_search();
        }
    };
}();

let KTAppEventProduct = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 3,
                    'render': function (data, type, row) { return renderGbnCell(data, type, row); }
                },
                {
                    'targets': 9,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' }, //순번
                { data: 'seq'}, //seq
                { data: 'awardYear' }, //수상연도
                { data: 'gbn' }, //분류
                { data: 'companyNameKo' }, //업체명
                { data: 'productNameKo' }, //제품명
                { data: 'homepage' }, //홈페이지
                { data: 'initRegiDttm' }, //등록일시
                { data: 'finalRegiDttm' }, //수정일시
                { data: 'actions' }
            ]
        });
    }

    function renderGbnCell(data, type, row){
        let gbn = row.gbn;
        let renderHTML = '-';
        if(nvl(gbn,'') !== ''){
            renderHTML = '';
            let resultArr = [];
            let gbnArr = gbn.split(',');
            for(let i=0; i<gbnArr.length; i++){
                let gbnVal = gbnArr[i];
                if(gbnVal === 'END_PRD'){
                    resultArr.push('완성품');
                }else if(gbnVal === 'PART_PRD'){
                    resultArr.push('부품');
                }else if(gbnVal === 'ABR_BOAT'){
                    resultArr.push('해외 요트/보트');
                }else if(gbnVal === 'SKILL_BU'){
                    resultArr.push('기술 [부품]');
                }else if(gbnVal === 'IMPRV_BU'){
                    resultArr.push('개선도 [부품]');
                }else if(gbnVal === 'IMPRV_END'){
                    resultArr.push('개선도 [완제품]');
                }else if(gbnVal === 'MARK_END'){
                    resultArr.push('시장성 [완제품]');
                }else if(gbnVal === 'IDEA_END'){
                    resultArr.push('아이디어 [완제품]');
                }else if(gbnVal === 'INVATE'){
                    resultArr.push('인비테이션어워드');
                }
            }

            for(let i=0; i<resultArr.length; i++){
                renderHTML += resultArr[i];
                if((i+1) !== resultArr.length){
                    renderHTML += '<br>';
                }
            }
        }
        return renderHTML;
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_event_product_modify_init_set(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">상세/수정</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_event_product_remove(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_event_product_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_event_product_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_event_product_search();
        }
    };
}();

let KTAppEventCompany = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 3,
                    'render': function (data, type, row) { return renderHomepageCell(data, type, row); }
                },
                {
                    'targets': 4,
                    'render': function (data, type, row) { return renderCateCell(data, type, row); }
                },
                {
                    'targets': 5,
                    'render': function (data, type, row) { return renderCateCell(data, type, row); }
                },
                {
                    'targets': 6,
                    'render': function (data, type, row) { return renderChargePersonNameCell(data, type, row); }
                },
                {
                    'targets': 7,
                    'render': function (data, type, row) { return renderCompanyNameCell(data, type, row); }
                },
                {
                    'targets': 10,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' }, //순번
                { data: 'seq'}, //seq
                { data: 'kibsYear' }, //KIBS Year
                { data: 'homepage' }, //홈페이지
                { data: 'categories' }, //카테고리
                { data: 'companyName' }, //회사명
                { data: 'chargePersonName' }, //담당자이름
                { data: 'chargePersonPhone' }, //휴대전화
                { data: 'initRegiDttm' }, //등록일시
                { data: 'finalRegiDttm' }, //수정일시
                { data: 'actions' }
            ]
        });
    }

    function renderChargePersonNameCell(data, type, row){
        let chargePersonName = row.chargePersonName;
        if(nvl(chargePersonName,'') === ''){
            chargePersonName = '-';
        }
        return chargePersonName;
    }

    function renderCompanyNameCell(data, type, row){
        let companyName = row.companyName;
        if(nvl(companyName,'') === ''){
            companyName = '-';
        }
        return companyName;
    }

    function renderCateCell(data, type, row){
        let categories = row.categories;
        if(nvl(categories,'') === ''){
            categories = '-';
        }
        return categories;
    }

    function renderHomepageCell(data, type, row){
        let homepage = row.homepage;
        if(nvl(homepage,'') === ''){
            homepage = '-';
        }
        return homepage;
    }

    function renderPhoneCell(data, type, row){
        let chargePersonPhone = row.chargePersonPhone;
        let chargePersonEmail = row.chargePersonEmail;
        if(nvl(chargePersonPhone,'') === ''){
            chargePersonPhone = '-';
        }
        if(nvl(chargePersonEmail,'') === ''){
            chargePersonEmail = '-';
        }
        return chargePersonPhone + ' / ' + chargePersonEmail;
    }

    function renderActionsCell(data, type, row){
        //console.log(row.seq);
        let rowSeq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_event_company_modify_init_set(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">상세/수정</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_event_company_remove(' + '\'' + rowSeq + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_event_company_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_event_company_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_event_company_search();
        }
    };
}();

let KTAppCenterBoardNotice = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 2,
                    'render': function (data) {
                        if (data === 'KO') {
                            return '국문'
                        } else {
                            return '영문'
                        }
                    }
                },
                {
                    'targets': [3,4,5,6],
                    'render': function (data) {
                        if (data === '1') {
                            return 'V'
                        } else {
                            return '-'
                        }
                    }
                },
                {
                    'targets': 12,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' },
                { data: 'id'},
                { data: 'lang'},
                { data: 'noticeGbn' },
                { data: 'gbn1' },
                { data: 'gbn2' },
                { data: 'gbn3' },
                { data: 'title' },
                { data: 'writer' },
                { data: 'writeDate' },
                { data: 'finalRegiDttm' },
                { data: 'viewCnt' },
                { data: 'actions' }
            ]
        });
    }

    function renderActionsCell(data, type, row){
        //console.log(row.id);
        let rowId = row.id;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div id="kt_menu" class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div id="kt_menu_item" class="menu-item px-3">';
        renderHTML += '<a onclick="f_notice_detail_modal_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3" data-bs-toggle="modal" data-bs-target="#kt_modal_modify_history">상세정보</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_notice_modify_init_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">수정</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_notice_remove(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_center_board_notice_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_center_board_notice_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_board_notice_search();
        }
    };
}();

let KTAppCenterBoardNewsletterKo = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': [2,3,4,5],
                    'render': function (data) {
                        if (data === '1') {
                            return 'V'
                        } else {
                            return '-'
                        }
                    }
                },
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 11,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' },
                { data: 'id'},
                { data: 'noticeGbn' },
                { data: 'gbn1' },
                { data: 'gbn2' },
                { data: 'gbn3' },
                { data: 'title' },
                { data: 'writer' },
                { data: 'writeDate' },
                { data: 'finalRegiDttm' },
                { data: 'viewCnt' },
                { data: 'actions' }
            ]
        });
    }

    function renderActionsCell(data, type, row){
        //console.log(row.id);
        let rowId = row.id;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div id="kt_menu" class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div id="kt_menu_item" class="menu-item px-3">';
        renderHTML += '<a onclick="f_newsletter_ko_detail_modal_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3" data-bs-toggle="modal" data-bs-target="#kt_modal_modify_history">상세정보</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_newsletter_ko_modify_init_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">수정</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_newsletter_ko_remove(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_center_board_newsletter_ko_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_center_board_newsletter_ko_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_board_newsletter_ko_search();
        }
    };
}();

let KTAppCenterBoardNewsletterEn = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'asc']],
            'columnDefs': [
                {
                    'targets': [2,3,4,5],
                    'render': function (data) {
                        if (data === '1') {
                            return 'V'
                        } else {
                            return '-'
                        }
                    }
                },
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 11,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' },
                { data: 'id'},
                { data: 'noticeGbn' },
                { data: 'gbn1' },
                { data: 'gbn2' },
                { data: 'gbn3' },
                { data: 'title' },
                { data: 'writer' },
                { data: 'writeDate' },
                { data: 'finalRegiDttm' },
                { data: 'viewCnt' },
                { data: 'actions' }
            ]
        });
    }

    function renderActionsCell(data, type, row){
        //console.log(row.id);
        let rowId = row.id;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div id="kt_menu" class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div id="kt_menu_item" class="menu-item px-3">';
        renderHTML += '<a onclick="f_newsletter_en_detail_modal_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3" data-bs-toggle="modal" data-bs-target="#kt_modal_modify_history">상세정보</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_newsletter_en_modify_init_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">수정</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_newsletter_en_remove(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_center_board_newsletter_en_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_center_board_newsletter_en_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            /* 조회 */
            f_board_newsletter_en_search();
        }
    };
}();

let KTAppCenterBoardPress = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': [2,3,4,5],
                    'render': function (data) {
                        if (data === '1') {
                            return 'V'
                        } else {
                            return '-'
                        }
                    }
                },
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 11,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' },
                { data: 'id'},
                { data: 'noticeGbn' },
                { data: 'gbn1' },
                { data: 'gbn2' },
                { data: 'gbn3' },
                { data: 'title' },
                { data: 'writer' },
                { data: 'writeDate' },
                { data: 'finalRegiDttm' },
                { data: 'viewCnt' },
                { data: 'actions' }
            ]
        });
    }

    function renderActionsCell(data, type, row){
        //console.log(row.id);
        let rowId = row.id;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div id="kt_menu" class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div id="kt_menu_item" class="menu-item px-3">';
        renderHTML += '<a onclick="f_press_detail_modal_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3" data-bs-toggle="modal" data-bs-target="#kt_modal_modify_history">상세정보</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_press_modify_init_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">수정</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_press_remove(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_center_board_press_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_center_board_press_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_board_press_search();
        }
    };
}();

let KTAppCenterBoardFaq = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 2,
                    'render': function (data) {
                        if (data === 'E') {
                            return '참가업체'
                        } else {
                            return '참관객'
                        }
                    }
                },
                {
                    'targets': 3,
                    'render': function (data) {
                        if (data === 'KO') {
                            return '국문'
                        } else {
                            return '영문'
                        }
                    }
                },
                {
                    'targets': 9,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' },
                { data: 'id'},
                { data: 'noticeGbn' },
                { data: 'lang' },
                { data: 'title' },
                { data: 'writer' },
                { data: 'writeDate' },
                { data: 'finalRegiDttm' },
                { data: 'viewCnt' },
                { data: 'actions' }
            ]
        });
    }

    function renderActionsCell(data, type, row){
        //console.log(row.id);
        let rowId = row.id;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div id="kt_menu" class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div id="kt_menu_item" class="menu-item px-3">';
        renderHTML += '<a onclick="f_faq_detail_modal_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3" data-bs-toggle="modal" data-bs-target="#kt_modal_modify_history">상세정보</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_faq_modify_init_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">수정</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_faq_remove(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_center_board_faq_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_center_board_faq_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_board_faq_search();
        }
    };
}();

let KTAppCenterBoardColumn = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': [2,3,4],
                    'render': function (data) {
                        if (data === '1') {
                            return 'V'
                        } else {
                            return '-'
                        }
                    }
                },
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 10,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' },
                { data: 'id'},
                { data: 'gbn1' },
                { data: 'gbn2' },
                { data: 'gbn3' },
                { data: 'title' },
                { data: 'writer' },
                { data: 'writeDate' },
                { data: 'finalRegiDttm' },
                { data: 'viewCnt' },
                { data: 'actions' }
            ]
        });
    }

    function renderActionsCell(data, type, row){
        //console.log(row.id);
        let rowId = row.id;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div id="kt_menu" class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div id="kt_menu_item" class="menu-item px-3">';
        renderHTML += '<a onclick="f_column_detail_modal_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3" data-bs-toggle="modal" data-bs-target="#kt_modal_modify_history">상세정보</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_column_modify_init_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">수정</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_column_remove(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_center_board_column_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_center_board_column_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_board_column_search();
        }
    };
}();

let KTAppCenterBoardBrochure = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'asc']],
            'columnDefs': [
                {
                    'targets': [2,3,4,5],
                    'render': function (data) {
                        if (data === '1') {
                            return 'V'
                        } else {
                            return '-'
                        }
                    }
                },
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 11,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' },
                { data: 'id'},
                { data: 'noticeGbn' },
                { data: 'gbn1' },
                { data: 'gbn2' },
                { data: 'gbn3' },
                { data: 'title' },
                { data: 'writer' },
                { data: 'writeDate' },
                { data: 'finalRegiDttm' },
                { data: 'viewCnt' },
                { data: 'actions' }
            ]
        });
    }

    function renderActionsCell(data, type, row){
        //console.log(row.id);
        let rowId = row.id;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div id="kt_menu" class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div id="kt_menu_item" class="menu-item px-3">';
        renderHTML += '<a onclick="f_brochure_detail_modal_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3" data-bs-toggle="modal" data-bs-target="#kt_modal_modify_history">상세정보</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_brochure_modify_init_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">수정</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_brochure_remove(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_center_board_brochure_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_center_board_brochure_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            /* 조회 */
            f_board_brochure_search();
        }
    };
}();

let KTAppCenterBoardDataroom = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': 2,
                    'render': function (data) {
                        if (data === '1') {
                            return '홈페이지'
                        } else {
                            return '-'
                        }
                    }
                },
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 9,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' },
                { data: 'id'},
                { data: 'siteGbn' },
                { data: 'mngYear' },
                { data: 'title' },
                { data: 'writer' },
                { data: 'writeDate' },
                { data: 'finalRegiDttm' },
                { data: 'viewCnt' },
                { data: 'actions' }
            ]
        });
    }

    function renderActionsCell(data, type, row){
        //console.log(row.id);
        let rowId = row.id;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div id="kt_menu" class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div id="kt_menu_item" class="menu-item px-3">';
        renderHTML += '<a onclick="f_dataroom_detail_modal_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3" data-bs-toggle="modal" data-bs-target="#kt_modal_modify_history">상세정보</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_dataroom_modify_init_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">수정</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_dataroom_remove(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_center_board_dataroom_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_center_board_dataroom_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_board_dataroom_search();
        }
    };
}();

let KTAppCenterBoardGallery = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'asc']],
            'columnDefs': [
                {
                    'targets': 2,
                    'render': function (data) {
                        if (data === '1') {
                            return '홈페이지'
                        } else {
                            return '-'
                        }
                    }
                },
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 9,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' },
                { data: 'id'},
                { data: 'siteGbn' },
                { data: 'mngYear' },
                { data: 'title' },
                { data: 'writer' },
                { data: 'writeDate' },
                { data: 'finalRegiDttm' },
                { data: 'viewCnt' },
                { data: 'actions' }
            ]
        });
    }

    function renderActionsCell(data, type, row){
        //console.log(row.id);
        let rowId = row.id;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div id="kt_menu" class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div id="kt_menu_item" class="menu-item px-3">';
        renderHTML += '<a onclick="f_gallery_detail_modal_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3" data-bs-toggle="modal" data-bs-target="#kt_modal_modify_history">상세정보</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_gallery_modify_init_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">수정</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_gallery_remove(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_center_board_gallery_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_center_board_gallery_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            /* 조회 */
            f_board_gallery_search();
        }
    };
}();

let KTAppCenterBoardKibstv = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': 2,
                    'render': function (data) {
                        if (data === 'KO') {
                            return '국문'
                        } else {
                            return '영문'
                        }
                    }
                },
                {
                    'targets': 3,
                    'render': function (data) {
                        if (data === '1') {
                            return '홈페이지'
                        } else {
                            return '-'
                        }
                    }
                },
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 9,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' },
                { data: 'id'},
                { data: 'lang' },
                { data: 'siteGbn' },
                { data: 'category' },
                { data: 'title' },
                { data: 'writer' },
                { data: 'writeDate' },
                { data: 'finalRegiDttm' },
                { data: 'actions' }
            ]
        });
    }

    function renderActionsCell(data, type, row){
        //console.log(row.id);
        let rowId = row.id;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div id="kt_menu" class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div id="kt_menu_item" class="menu-item px-3">';
        renderHTML += '<a onclick="f_kibstv_detail_modal_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3" data-bs-toggle="modal" data-bs-target="#kt_modal_modify_history">상세정보</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_kibstv_modify_init_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">수정</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_board_kibstv_remove(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_center_board_kibstv_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_center_board_kibstv_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_board_kibstv_search();
        }
    };
}();

let KTAppCenterPopupAdd = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 2,
                    'render': function (data) {
                        if (data === 'KO') {
                            return '국문'
                        } else {
                            return '영문'
                        }
                    }
                },
                {
                    'targets': 9,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' },
                { data: 'id'},
                { data: 'lang'},
                { data: 'useYn'},
                { data: 'title' },
                { data: 'publishedDate' },
                { data: 'expirationDate' },
                { data: 'initRegiDttm' },
                { data: 'finalRegiDttm' },
                { data: 'actions' }
            ]
        });
    }

    function renderActionsCell(data, type, row){
        //console.log(row.id);
        let rowId = row.id;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div id="kt_menu" class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_center_popup_modify_init_set(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">수정</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_center_popup_remove(' + '\'' + rowId + '\'' + ')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_center_popup_add_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_center_popup_add_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_center_popup_search();
        }
    };
}();

let KTAppCenterDocumentDownload = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            "info": false,
            'order': [],
            'pageLength': 10,
            'columnDefs': [
                //{ render: DataTable.render.number(',', '.', 2), targets: 4},
                // { orderable: false, targets: 0 }, // Disable ordering on column 0 (checkbox)
                { orderable: false, targets: 1 }, // Disable ordering on column 1 (유형)
                { orderable: false, targets: 7 }, // Disable ordering on column 7 (내려받기)
                { orderable: false, targets: 8 }, // Disable ordering on column 8 (삭제)
            ]
        });

        // Re-init functions on datatable re-draws
        datatable.on('draw', function () {
            handleDeleteRows();
        });
    }

    // Delete cateogry
    let handleDeleteRows = () => {
        // Select all delete buttons
        const deleteButtons = table.querySelectorAll('[data-kt-ecommerce-product-filter="delete_row"]');

        deleteButtons.forEach(d => {
            // Delete button on click
            d.addEventListener('click', function (e) {
                e.preventDefault();

                // Select parent row
                const parent = e.target.closest('tr');

                // Get category name
                const productName = parent.querySelector('[data-kt-ecommerce-product-filter="product_name"]').innerText;

                // SweetAlert2 pop up --- official docs reference: https://sweetalert2.github.io/
                Swal.fire({
                    text: "Are you sure you want to delete " + productName + "?",
                    icon: "warning",
                    showCancelButton: true,
                    buttonsStyling: false,
                    confirmButtonText: "Yes, delete!",
                    cancelButtonText: "No, cancel",
                    customClass: {
                        confirmButton: "btn fw-bold btn-danger",
                        cancelButton: "btn fw-bold btn-active-light-primary"
                    }
                }).then(function (result) {
                    if (result.value) {
                        Swal.fire({
                            text: "You have deleted " + productName + "!.",
                            icon: "success",
                            buttonsStyling: false,
                            confirmButtonText: "Ok, got it!",
                            customClass: {
                                confirmButton: "btn fw-bold btn-primary",
                            }
                        }).then(function () {
                            // Remove current row
                            datatable.row($(parent)).remove().draw();
                        });
                    } else if (result.dismiss === 'cancel') {
                        Swal.fire({
                            text: productName + " was not deleted.",
                            icon: "error",
                            buttonsStyling: false,
                            confirmButtonText: "Ok, got it!",
                            customClass: {
                                confirmButton: "btn fw-bold btn-primary",
                            }
                        });
                    }
                });
            })
        });
    }


    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_center_document_download_table');

            if (!table) {
                return;
            }

            initDatatable();
            handleDeleteRows();
        }
    };
}();

let KTAppCenterEdmList = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 2,
                    'render': function (data, type, row) { return renderTitleCell(data, type, row); }
                },
                {
                    'targets': 3,
                    'render': function (data, type, row) { return renderSendContentCell(data, type, row); }
                },
                {
                    'targets': 4,
                    'render': function (data, type, row){ return renderSendCntCell(data, type, row); }
                },
                {
                    'targets': 6,
                    'render': function (data, type, row) { return renderBadgeCell(data, type, row); }
                },
                {
                    'targets': 7,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' },
                { data: 'id'},
                { data: 'title'},
                { data: 'sendContent' },
                { data: 'sendCnt' },
                { data: 'sendDttm' },
                { data: 'sendResult' },
                { data: 'actions'}
            ]
        });
    }

    function renderTitleCell(data, type, row){
        let title = row.title;
        if(nvl(title,'') !== ''){
            if(title.length > 30){
                title = title.substring(0, 30) + '...';
            }
        }else{
            title = '-';
        }
        return title;
    }

    function renderSendContentCell(data, type, row){

        let sendContent = row.sendContent;

        let renderHTML = '';
        if(nvl(sendContent,'') !== ''){
            renderHTML = '<a class="text-gray-800 text-hover-primary fs-5 fw-bold text-decoration-none"';
            renderHTML += 'onclick="f_edm_send_content_set(\'' + sendContent + '\')" data-bs-toggle="modal" data-bs-target="#kt_modal_send_content" style="cursor: pointer;">';
            let reg = /<[^>]*>?/g;
            sendContent = sendContent.replaceAll(reg, '');
            if(sendContent.length > 40){
                sendContent = sendContent.substring(0, 40) + '...';
            }
        }else{
            sendContent = '-';
            renderHTML = '<a class="text-gray-800 text-hover-primary fs-5 fw-bold text-decoration-none"';
            renderHTML += 'onclick="f_edm_send_content_set(\'' + sendContent + '\')" data-bs-toggle="modal" data-bs-target="#kt_modal_send_content" style="cursor: pointer;">';
        }
        renderHTML += sendContent;
        renderHTML += '</a>';

        return renderHTML;
    }

    function renderSendCntCell(data, type, row){
        return row.sendCnt + ' 건';
    }

    function renderBadgeCell(data, type, row){
        let sendResult = row.sendResult; //성공:12 / 실패:0
        let sendSuc = sendResult.split(' / ')[0];
        let sendFail = sendResult.split(' / ')[1];
        let renderHTML = '';
        renderHTML += '<div class="badge badge-light-primary fw-bold mr10">';
        renderHTML += sendSuc;
        renderHTML += '</div>';
        renderHTML += '<div class="badge badge-light-danger fw-bold">';
        renderHTML += sendFail;
        renderHTML += '</div>';
        return renderHTML;
    }

    function renderActionsCell(data, type, row){
        let id = row.id;
        let renderHTML = '';
        renderHTML += '<a onclick="f_edm_hst_remove(' + '\'' + id + '\'' + ')" class="cursor-pointer">';
        renderHTML += '<i class="ki-solid ki-cross-square fs-2hx text-danger"></i>';
        renderHTML += '</a>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_center_edm_list_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_center_edm_list_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_edm_hst_search();
        }
    };
}();

let KTAppCenterEdmUploadList = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': false,
            'ordering': true,
            "scrollY": "250px",
            "scrollCollapse": true,
            'order': [[0, 'asc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                { visible: false, targets: [1] }
            ],
            columns: [
                { data: 'rownum' },
                { data: 'id'},
                { data: 'email'},
                { data: 'name' },
                { data: 'phone' }
            ]
        });
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_center_edm_upload_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#kt_center_edm_upload_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);
        }
    };
}();

let KTAppRequestManagementList = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            "info": false,
            'order': [],
            'pageLength': 10,
            'columnDefs': [
                //{ render: DataTable.render.number(',', '.', 2), targets: 4},
                // { orderable: false, targets: 0 }, // Disable ordering on column 0 (checkbox)
                { orderable: false, targets: 5 }, // Disable ordering on column 5 (진행상황)
            ]
        });

        // Re-init functions on datatable re-draws
        datatable.on('draw', function () {
            handleDeleteRows();
        });
    }

    // Search Datatable --- official docs reference: https://datatables.net/reference/api/search()
    let handleSearchDatatable = () => {
        const filterSearch = document.querySelector('[data-kt-ecommerce-product-filter="search"]');
        filterSearch.addEventListener('keyup', function (e) {
            datatable.search(e.target.value).draw();
        });
    }

    // Handle status filter dropdown
    let handleStatusFilter = () => {
        const filterStatus = document.querySelector('[data-kt-ecommerce-product-filter="status"]');
        $(filterStatus).on('change', e => {
            let value = e.target.value;
            if(value === 'all'){
                value = '';
            }
            datatable.column(6).search(value).draw();
        });
    }

    // Delete cateogry
    let handleDeleteRows = () => {
        // Select all delete buttons
        const deleteButtons = table.querySelectorAll('[data-kt-ecommerce-product-filter="delete_row"]');

        deleteButtons.forEach(d => {
            // Delete button on click
            d.addEventListener('click', function (e) {
                e.preventDefault();

                // Select parent row
                const parent = e.target.closest('tr');

                // Get category name
                const productName = parent.querySelector('[data-kt-ecommerce-product-filter="product_name"]').innerText;

                // SweetAlert2 pop up --- official docs reference: https://sweetalert2.github.io/
                Swal.fire({
                    text: "Are you sure you want to delete " + productName + "?",
                    icon: "warning",
                    showCancelButton: true,
                    buttonsStyling: false,
                    confirmButtonText: "Yes, delete!",
                    cancelButtonText: "No, cancel",
                    customClass: {
                        confirmButton: "btn fw-bold btn-danger",
                        cancelButton: "btn fw-bold btn-active-light-primary"
                    }
                }).then(function (result) {
                    if (result.value) {
                        Swal.fire({
                            text: "You have deleted " + productName + "!.",
                            icon: "success",
                            buttonsStyling: false,
                            confirmButtonText: "Ok, got it!",
                            customClass: {
                                confirmButton: "btn fw-bold btn-primary",
                            }
                        }).then(function () {
                            // Remove current row
                            datatable.row($(parent)).remove().draw();
                        });
                    } else if (result.dismiss === 'cancel') {
                        Swal.fire({
                            text: productName + " was not deleted.",
                            icon: "error",
                            buttonsStyling: false,
                            confirmButtonText: "Ok, got it!",
                            customClass: {
                                confirmButton: "btn fw-bold btn-primary",
                            }
                        });
                    }
                });
            })
        });
    }


    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_request_management_list_table');

            if (!table) {
                return;
            }

            initDatatable();
            /*handleSearchDatatable();
            handleStatusFilter();
            handleDeleteRows();*/
        }
    };
}();

let KTAppRequestManagementAdd = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            "info": false,
            'order': [],
            'pageLength': 10,
            'columnDefs': [
                //{ render: DataTable.render.number(',', '.', 2), targets: 4},
                // { orderable: false, targets: 0 }, // Disable ordering on column 0 (checkbox)
                { orderable: false, targets: 5 }, // Disable ordering on column 5 (진행상황)
            ]
        });

        // Re-init functions on datatable re-draws
        datatable.on('draw', function () {
            handleDeleteRows();
        });
    }

    // Search Datatable --- official docs reference: https://datatables.net/reference/api/search()
    let handleSearchDatatable = () => {
        const filterSearch = document.querySelector('[data-kt-ecommerce-product-filter="search"]');
        filterSearch.addEventListener('keyup', function (e) {
            datatable.search(e.target.value).draw();
        });
    }

    // Handle status filter dropdown
    let handleStatusFilter = () => {
        const filterStatus = document.querySelector('[data-kt-ecommerce-product-filter="status"]');
        $(filterStatus).on('change', e => {
            let value = e.target.value;
            if(value === 'all'){
                value = '';
            }
            datatable.column(6).search(value).draw();
        });
    }

    // Delete cateogry
    let handleDeleteRows = () => {
        // Select all delete buttons
        const deleteButtons = table.querySelectorAll('[data-kt-ecommerce-product-filter="delete_row"]');

        deleteButtons.forEach(d => {
            // Delete button on click
            d.addEventListener('click', function (e) {
                e.preventDefault();

                // Select parent row
                const parent = e.target.closest('tr');

                // Get category name
                const productName = parent.querySelector('[data-kt-ecommerce-product-filter="product_name"]').innerText;

                // SweetAlert2 pop up --- official docs reference: https://sweetalert2.github.io/
                Swal.fire({
                    text: "Are you sure you want to delete " + productName + "?",
                    icon: "warning",
                    showCancelButton: true,
                    buttonsStyling: false,
                    confirmButtonText: "Yes, delete!",
                    cancelButtonText: "No, cancel",
                    customClass: {
                        confirmButton: "btn fw-bold btn-danger",
                        cancelButton: "btn fw-bold btn-active-light-primary"
                    }
                }).then(function (result) {
                    if (result.value) {
                        Swal.fire({
                            text: "You have deleted " + productName + "!.",
                            icon: "success",
                            buttonsStyling: false,
                            confirmButtonText: "Ok, got it!",
                            customClass: {
                                confirmButton: "btn fw-bold btn-primary",
                            }
                        }).then(function () {
                            // Remove current row
                            datatable.row($(parent)).remove().draw();
                        });
                    } else if (result.dismiss === 'cancel') {
                        Swal.fire({
                            text: productName + " was not deleted.",
                            icon: "error",
                            buttonsStyling: false,
                            confirmButtonText: "Ok, got it!",
                            customClass: {
                                confirmButton: "btn fw-bold btn-primary",
                            }
                        });
                    }
                });
            })
        });
    }


    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_request_management_add_table');

            if (!table) {
                return;
            }

            initDatatable();
            /*handleSearchDatatable();
            handleStatusFilter();
            handleDeleteRows();*/
        }
    };
}();

let KTAppRequestManagementQuestion = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            "info": false,
            'order': [],
            'pageLength': 10,
            'columnDefs': [
                //{ render: DataTable.render.number(',', '.', 2), targets: 4},
                // { orderable: false, targets: 0 }, // Disable ordering on column 0 (checkbox)
                { orderable: false, targets: 3 }, // Disable ordering on column 3 (기능)
            ]
        });

        // Re-init functions on datatable re-draws
        datatable.on('draw', function () {
            handleDeleteRows();
        });
    }

    // Search Datatable --- official docs reference: https://datatables.net/reference/api/search()
    let handleSearchDatatable = () => {
        const filterSearch = document.querySelector('[data-kt-ecommerce-product-filter="search"]');
        filterSearch.addEventListener('keyup', function (e) {
            datatable.search(e.target.value).draw();
        });
    }

    // Handle status filter dropdown
    let handleStatusFilter = () => {
        const filterStatus = document.querySelector('[data-kt-ecommerce-product-filter="status"]');
        $(filterStatus).on('change', e => {
            let value = e.target.value;
            if(value === 'all'){
                value = '';
            }
            datatable.column(6).search(value).draw();
        });
    }

    // Delete cateogry
    let handleDeleteRows = () => {
        // Select all delete buttons
        const deleteButtons = table.querySelectorAll('[data-kt-ecommerce-product-filter="delete_row"]');

        deleteButtons.forEach(d => {
            // Delete button on click
            d.addEventListener('click', function (e) {
                e.preventDefault();

                // Select parent row
                const parent = e.target.closest('tr');

                // Get category name
                const productName = parent.querySelector('[data-kt-ecommerce-product-filter="product_name"]').innerText;

                // SweetAlert2 pop up --- official docs reference: https://sweetalert2.github.io/
                Swal.fire({
                    text: "Are you sure you want to delete " + productName + "?",
                    icon: "warning",
                    showCancelButton: true,
                    buttonsStyling: false,
                    confirmButtonText: "Yes, delete!",
                    cancelButtonText: "No, cancel",
                    customClass: {
                        confirmButton: "btn fw-bold btn-danger",
                        cancelButton: "btn fw-bold btn-active-light-primary"
                    }
                }).then(function (result) {
                    if (result.value) {
                        Swal.fire({
                            text: "You have deleted " + productName + "!.",
                            icon: "success",
                            buttonsStyling: false,
                            confirmButtonText: "Ok, got it!",
                            customClass: {
                                confirmButton: "btn fw-bold btn-primary",
                            }
                        }).then(function () {
                            // Remove current row
                            datatable.row($(parent)).remove().draw();
                        });
                    } else if (result.dismiss === 'cancel') {
                        Swal.fire({
                            text: productName + " was not deleted.",
                            icon: "error",
                            buttonsStyling: false,
                            confirmButtonText: "Ok, got it!",
                            customClass: {
                                confirmButton: "btn fw-bold btn-primary",
                            }
                        });
                    }
                });
            })
        });
    }


    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_request_management_question_table');

            if (!table) {
                return;
            }

            initDatatable();
            /*handleSearchDatatable();
            handleStatusFilter();
            handleDeleteRows();*/
        }
    };
}();

let KTAppParticipantCompanyList = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {

        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : false,
            'select': true,
            'ordering': true,
            'order': [[0, 'desc']],
            'columnDefs': [
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                { visible: false, targets: [1,2,3,4,5,6,9,10,16,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49] }
            ],
            columns: [
                { data: 'rownum' },
                { data: 'chargePersonRep1' }, //1차 담당자1
                { data: 'chargePersonRep2' }, //2차 담당자2
                { data: 'booth24' }, //2024 예상부스3
                { data: 'tmReference24' }, //2024 TM4
                { data: 'tmReference23' }, //2023 TM5
                { data: 'partWantYn' }, //참가의향6
                { data: 'boothCnt23' }, //2023 부스수@7
                { data: 'prePartYear' }, //출처/참가년도@8
                { data: 'companyGbn' }, //기업유형9
                { data: 'companyRegion' }, //지역10
                { data: 'companyNameKo' }, //회사명@11
                { data: 'companyHomepage' }, //업체홈페이지주소@12
                { data: 'companyAddress' }, //업체주소@13
                { data: 'companyCeo' }, //대표@14
                { data: 'fieldPart' }, //참가분야@15
                { data: 'classify' }, //분류16
                { data: 'displayItem' }, //전시품목@17
                { data: 'companyFax' }, //팩스@18
                { data: 'chargePersonName1' }, //담당자명1@19
                { data: 'chargePersonPosition1' }, //담당자직위1@20
                { data: 'chargePersonTel1' }, //담당자전화번호1@21
                { data: 'chargePersonPhone1' }, //담당자휴대전화1@22
                { data: 'chargePersonEmail1' }, //담당자이메일1@23
                { data: 'chargePersonName2' }, //담당자명2@24
                { data: 'chargePersonPosition2' }, //담당자직위2@25
                { data: 'chargePersonTel2' }, //담당자전화번호2@26
                { data: 'chargePersonPhone2' }, //담당자휴대전화2@27
                { data: 'chargePersonEmail2' }, //담당자이메일2@28
                { data: 'chargePersonName3' }, //담당자명3@29
                { data: 'chargePersonPosition3' }, //담당자직위3@30
                { data: 'chargePersonTel3' }, //담당자전화번호3@31
                { data: 'chargePersonPhone3' }, //담당자휴대전화3@32
                { data: 'chargePersonEmail3' }, //담당자이메일3@33
                { data: 'companyNote' }, //업체비고34
                { data: 'businessStatusLarge1' }, //업태1-대분류35
                { data: 'businessStatusSmall1' }, //업태1-중분류36
                { data: 'businessStatusLarge2' }, //업태2-대분류37
                { data: 'businessStatusSmall2' }, //업태2-중분류38
                { data: 'businessStatusLarge3' }, //업태3-대분류39
                { data: 'businessStatusSmall3' }, //업태3-중분류40
                { data: 'businessType' }, //영위사업41
                { data: 'collectionType' }, //수집유형42
                { data: 'intentionStep' }, //의향단계43
                { data: 'contactProgressYn' }, //컨택진행여부44
                { data: 'contactDttm' }, //컨택일자45
                { data: 'contactResult' }, //컨택결과46
                { data: 'contactTargetName' }, //컨택대상자성명47
                { data: 'contactTargetPhone' }, //컨택대상자연락처48
                { data: 'contactResultDetail' } //컨택결과세부내용49
            ]
        });
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#kt_participant_company_table');

            if (!table) {
                return;
            }

            initDatatable();
            let dataTbl = $('#kt_participant_company_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 0, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 테이블 default all 조회*/
            f_exhibitor_comp_search();

            $('#kt_participant_company_table tbody').on('click', 'tr', function () {
                let dataRow = dataTbl.row( this ).data();
                let classList = this.classList;
                if (classList.contains('selected')) {
                    /*수정버튼*/
                    document.getElementById('exhibitor_comp_modify_btn').classList.remove('btn-dark');
                    document.getElementById('exhibitor_comp_modify_btn').classList.remove('btn-active-light-dark');
                    document.getElementById('exhibitor_comp_modify_btn').classList.add('btn-secondary');
                    /*삭제버튼*/
                    document.getElementById('exhibitor_comp_remove_btn').classList.remove('btn-danger');
                    document.getElementById('exhibitor_comp_remove_btn').classList.remove('btn-active-light-danger');
                    document.getElementById('exhibitor_comp_remove_btn').classList.add('btn-secondary');
                } else {
                    if(nvl(dataRow,'') !== ''){
                        /*수정버튼*/
                        document.getElementById('exhibitor_comp_modify_btn').classList.remove('btn-secondary');
                        document.getElementById('exhibitor_comp_modify_btn').classList.add('btn-dark');
                        document.getElementById('exhibitor_comp_modify_btn').classList.add('btn-active-light-dark');
                        /*삭제버튼*/
                        document.getElementById('exhibitor_comp_remove_btn').classList.remove('btn-secondary');
                        document.getElementById('exhibitor_comp_remove_btn').classList.add('btn-danger');
                        document.getElementById('exhibitor_comp_remove_btn').classList.add('btn-active-light-danger');
                    }
                }
            });
        }
    };
}();

let DTRequestManagementList = function () {
    // Shared variables
    let table;
    let datatable;

    // Private functions
    let initDatatable = function () {
        // Init datatable --- more info on datatables: https://datatables.net/manual/
        datatable = $(table).DataTable({
            'info': false,
            'paging' : true,
            'select': false,
            'ordering': true,
            'order': [[1, 'desc']],
            'columnDefs': [
                { orderable: false, targets: 0 }, // Disable ordering on column 0 (checkbox)
                {
                    'targets': '_all',
                    'className': 'text-center'
                },
                {
                    'targets': 0,
                    'render': function (data, type, row) { return renderCheckBoxCell(data, type, row); }
                },
                {
                    'targets': 3,
                    'render': function (data, type, row) { return renderEmergencyYnCell(data, type, row); }
                },
                {
                    'targets': 5,
                    'render': function (data, type, row) { return renderProgressStepCell(data, type, row); }
                },
                {
                    'targets': 6,
                    'render': function (data, type, row) { return renderTitleCell(data, type, row); }
                },
                {
                    'targets': 7,
                    'render': function (data, type, row) { return renderCompleteExpectDateCell(data, type, row); }
                },
                {
                    'targets': 8,
                    'render': function (data, type, row) { return renderWriterCell(data, type, row); }
                },
                {
                    'targets': 10,
                    'data': 'actions',
                    'render': function (data, type, row) { return renderActionsCell(data, type, row); }
                },
                { visible: false, targets: [2] }
            ],
            columns: [
                { data: '' },
                { data: 'rownum' },
                { data: 'seq'},
                { data: 'emergencyYn' },
                { data: 'gbn'},
                { data: 'progressStep'},
                { data: 'title'},
                { data: 'completeExpectDate'},
                { data: 'writer'},
                { data: 'initRegiDttm' },
                { data: 'actions' }
            ]
        });
    }

    function renderCheckBoxCell(data, type, row){
        let renderHTML = '-';
        let title = row.title;
        if(nvl(title,'') !== ''){
            renderHTML = '<div class="request_check form-check form-check-sm form-check-custom form-check-solid">' +
                '<input class="form-check-input" type="checkbox" value="'+ row.seq +'" data-value="' + row.progressStep + ' / ' + title + '"/>' +
                '</div>';
        }
        return renderHTML;
    }

    function renderEmergencyYnCell(data, type, row){
        let emergencyYn = row.emergencyYn;
        let renderHTML = '';
        if(nvl(emergencyYn,'N') === 'N'){
            renderHTML = '<div class="badge badge-secondary fw-bold">';
            renderHTML += '일반';
            renderHTML += '</div>';
        }else{
            renderHTML = '<div class="badge badge-danger fw-bold">';
            renderHTML += '긴급';
            renderHTML += '</div>';
        }

        return renderHTML;
    }

    function renderProgressStepCell(data, type, row) {
        let progressStep = row.progressStep;
        let renderHTML = '';
        if(nvl(progressStep,'') === ''){
            renderHTML = '-';
        }else{
            switch (progressStep){
                case '처리대기':
                    renderHTML = '<div class="badge badge-light-success fw-bold">';
                    break;
                case '진행중':
                    renderHTML = '<div class="badge badge-light-primary fw-bold">';
                    break;
                case '완료':
                    renderHTML = '<div class="badge badge-light-secondary fw-bold">';
                    break;
                case '논의필요':
                    renderHTML = '<div class="badge badge-light-warning fw-bold">';
                    break;
                case '처리불가':
                    renderHTML = '<div class="badge badge-light-danger fw-bold">';
                    break;
                default:
                    renderHTML = '<div>-';
                    break;
            }

            renderHTML += progressStep;
            renderHTML += '</div>';
        }

        return renderHTML;
    }

    function renderCompleteExpectDateCell(data, type, row) {
        let completeExpectDate = row.completeExpectDate;
        let renderHTML = '-';
        if(nvl(completeExpectDate,'') !== ''){
            renderHTML = completeExpectDate;
        }

        return renderHTML;
    }

    function renderTitleCell(data, type, row) {
        let seq = row.seq;
        let title = row.title;
        let replyCnt = row.replyCnt;
        let renderHTML = '-';
        if(nvl(title,'') !== ''){
            renderHTML = '<span class="fw-bold">';
            renderHTML += '<a href="javascript:void(0);" onclick="f_request_list_detail_set(\'' + seq + '\')">';
            renderHTML += title;
            if(nvl(replyCnt,'0') !== '0'){
                renderHTML += ' ( ' + replyCnt + ' )';
            }
            renderHTML += '</a>';
            renderHTML += '</span>';
        }

        return renderHTML;
    }

    function renderWriterCell(data, type, row) {
        let writer = row.writer;
        let renderHTML = '-';
        if(nvl(writer,'') !== ''){
            if(writer === 'meetingfan'){
                renderHTML = '개발사';
            }else{
                renderHTML = '관리자';
            }
        }

        return renderHTML;
    }

    function renderActionsCell(data, type, row){
        //console.log(row.id);
        let seq = row.seq;
        let renderHTML = '<button type="button" onclick="KTMenu.createInstances()" class="btn btn-sm btn-light btn-flex btn-center btn-active-light-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">';
        renderHTML += 'Actions';
        renderHTML += '<i class="ki-duotone ki-down fs-5 ms-1"></i></button>';
        renderHTML += '<div id="kt_menu" class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-600 menu-state-bg-light-primary fw-semibold fs-7 w-150px py-4" data-kt-menu="true">';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_request_list_detail_set(\'' + seq + '\')" class="menu-link px-3">상세/수정</a>';
        renderHTML += '</div>';
        renderHTML += '<div class="menu-item px-3">';
        renderHTML += '<a onclick="f_request_list_remove(\'' + seq + '\')" class="menu-link px-3">삭제</a>';
        renderHTML += '</div>';
        renderHTML += '</div>';
        return renderHTML;
    }

    // Public methods
    return {
        init: function () {
            table = document.querySelector('#mng_request_list_table');

            if (!table) {
                return;
            }

            initDatatable();

            /* Data row clear */
            let dataTbl = $('#mng_request_list_table').DataTable();
            dataTbl.clear();
            dataTbl.draw(false);

            dataTbl.on('order.dt search.dt', function () {
                let i = dataTbl.rows().count();
                dataTbl.cells(null, 1, { search: 'applied', order: 'applied' })
                    .every(function (cell) {
                        this.data(i--);
                    });
            }).draw();

            /* 조회 */
            f_request_list_search();
        }
    };
}();

// On document ready
KTUtil.onDOMContentLoaded(function () {

    // 전시회>참가자관리
    KTAppExhibitorMng.init(); // /mng/exhibitor/participant/company.do
    KTAppVisitorMng.init(); // /mng/exhibitor/participant/visitor.do
    KTAppMemberMng.init(); // /mng/exhibitor/participant/member.do

    // 전시회>이관정보
    KTAppExhibitorTransferCompany.init(); // /mng/exhibitor/transfer/company.do
    KTAppExhibitorTransferVisitor.init(); // /mng/exhibitor/transfer/visitor.do

    // 전시회>참가신청서관리
    KTAppExhibitorApplicationBooth.init(); // /mng/exhibitor/application/booth.do
    KTAppExhibitorApplicationSign.init(); // /mng/exhibitor/application/sign.do
    KTAppExhibitorApplicationUtility.init(); // /mng/exhibitor/application/utility.do
    KTAppExhibitorApplicationPass.init(); // /mng/exhibitor/application/pass.do
    KTAppExhibitorApplicationBuyer.init(); // /mng/exhibitor/application/buyer.do
    KTAppExhibitorApplicationGift.init(); // /mng/exhibitor/application/gift.do
    KTAppExhibitorApplicationBanner.init(); // /mng/exhibitor/application/banner.do
    KTAppExhibitorApplicationOnline.init(); // /mng/exhibitor/application/online.do

    // 부대행사>올해의제품
    KTAppEventProduct.init(); // /mng/event/product.do
    KTAppEventCompany.init(); // /mng/event/company.do

    // 정보센터>게시판관리
    KTAppCenterBoardNotice.init(); // /mng/center/board/notice.do
    KTAppCenterBoardNewsletterKo.init(); // /mng/center/board/newsletter_ko.do
    KTAppCenterBoardNewsletterEn.init(); // /mng/center/board/newsletter_en.do
    KTAppCenterBoardPress.init(); // /mng/center/board/press.do
    KTAppCenterBoardFaq.init(); // /mng/center/board/faq.do
    KTAppCenterBoardColumn.init(); // /mng/center/board/column.do
    KTAppCenterBoardBrochure.init(); // /mng/center/board/brochure.do
    KTAppCenterBoardDataroom.init(); // /mng/center/board/dataroom.do
    KTAppCenterBoardGallery.init(); // /mng/center/board/gallery.do
    KTAppCenterBoardKibstv.init(); // /mng/center/board/kibstv.do

    // 정보센터>팝업관리
    KTAppCenterPopupAdd.init(); // /mng/center/popup/add.do

    // 정보센터>나의서류함
    KTAppCenterDocumentDownload.init(); // /mng/center/document/download.do

    // 정보센터>eDM관리
    KTAppCenterEdmList.init(); // /mng/center/edm/list.do
    KTAppCenterEdmUploadList.init(); //mng/center/edm/list/detail.do

    // 요청사항>요청사항관리
    KTAppRequestManagementList.init(); // /mng/request/management/list.do
    KTAppRequestManagementAdd.init(); // /mng/request/management/add.do
    KTAppRequestManagementQuestion.init(); // /mng/request/management/question.do

    // 참가업체>참가업체관리
    KTAppParticipantCompanyList.init(); // /mng/participant/company/list.do

    // 개발사>요청사항 & 문의
    DTRequestManagementList.init(); // /mng/request/management/list.do

});