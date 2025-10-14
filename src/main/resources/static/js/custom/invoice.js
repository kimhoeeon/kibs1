$(function(){
    // --- ▼▼▼ [신규] 페이지 로드 시 맨 아래로 스크롤하는 로직 ▼▼▼ ---
    if (sessionStorage.getItem('scrollToBottom') === 'true') {
        // 애니메이션 효과와 함께 페이지 맨 아래로 부드럽게 스크롤
        $('html, body').animate({ scrollTop: $(document).height() }, "slow");

        // 사용한 명령은 즉시 제거하여, 다음 새로고침 시에는 실행되지 않도록 함
        sessionStorage.removeItem('scrollToBottom');
    }
    // --- ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ ---


    // 전역에서 사용할 참가업체 SEQ (JSP의 body 태그에 data-exhibitor-seq="..."가 있어야 함)
    const exhibitorSeq = $('body').data('exhibitor-seq');

    $(document).on('change', 'input[name="invoiceSeq"]', function() {
        // 만약 현재 체크박스가 선택되었다면,
        if (this.checked) {
            // 다른 모든 'invoiceSeq' 체크박스의 선택을 해제합니다.
            $('input[name="invoiceSeq"]').not(this).prop('checked', false);
        }
    });

    /******************************************************
     * 특별 할인 관련 로직
     ******************************************************/

    // 1. '특별 할인 변경내용저장' 버튼 클릭 이벤트
    $('#saveSpecialDiscountBtn').on('click', function() {
        const saveData = {
            seq: exhibitorSeq,
            discountSpecial1Yn: $('#discountSpecial1Yn').is(':checked'),
            discountSpecial1Note: $('#discountSpecial1Note').val(),
            discountSpecial2Yn: $('#discountSpecial2Yn').is(':checked'),
            discountSpecial2Reason: $('#discountSpecial2Reason').val(),
            discountSpecial2Amount: parseInt($('#discountSpecial2Amount').val().replace(/,/g, '')) || 0,
            discountSpecial2Note: $('#discountSpecial2Note').val(),
            discountSpecial3Yn: $('#discountSpecial3Yn').is(':checked'),
            discountSpecial3Reason: $('#discountSpecial3Reason').val(),
            discountSpecial3Amount: parseInt($('#discountSpecial3Amount').val().replace(/,/g, '')) || 0,
            discountSpecial3Note: $('#discountSpecial3Note').val()
        };

        $.ajax({
            url: '/mng/exhibitorNew/participant/company/invoice/saveSpecialDiscount.do',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(saveData),
            success: function(response) {
                if(response.resultCode === "0") {
                    alert(response.resultMsg);
                    location.reload();
                } else {
                    alert(response.resultMsg || '저장 중 오류가 발생했습니다.');
                }
            },
            error: function() {
                alert('서버 통신 중 오류가 발생했습니다.');
            }
        });
    });

    // 2. 특별 할인 항목들의 변경을 감지하여 '계산서'를 다시 계산하는 함수
    function recalculateFinalTotal() {
        const boothSum = parseInt($('#baseBoothSum').val()) || 0;
        const utilitySum = parseInt($('#baseUtilitySum').val()) || 0;
        const basicDiscountSum = parseInt($('#baseDiscountSum').val()) || 0;
        let specialDiscountTotal = 0;

        const baseAmountForSpecial = boothSum + utilitySum - basicDiscountSum;

        if ($('#discountSpecial1Yn').is(':checked')) {
            specialDiscountTotal += Math.floor(baseAmountForSpecial * 0.5);
        }
        $('.special-discount-amount').each(function() {
            const isChecked = $(this).closest('tr').find('.special-discount-checkbox').is(':checked');
            if (isChecked) {
                const amount = parseInt($(this).val().replace(/,/g, '')) || 0;
                specialDiscountTotal += amount;
            }
        });

        const subtotal = baseAmountForSpecial - specialDiscountTotal;
        const vat = Math.floor(subtotal * 0.1);
        const finalTotal = subtotal + vat;

        $('#summary_special_discount').text('- ￦ ' + specialDiscountTotal.toLocaleString());
        $('#summary_subtotal').text('￦ ' + subtotal.toLocaleString());
        $('#summary_vat').text('￦ ' + vat.toLocaleString());
        $('#summary_final_total').text('￦ ' + finalTotal.toLocaleString());
    }

    // 3. 특별 할인 관련 UI가 변경될 때마다 실시간으로 총액을 다시 계산
    $('#kt_discount_special_info').on('change input', '.special-discount-checkbox, .special-discount-amount', recalculateFinalTotal);

    // 4. 페이지 로드 시 특별 할인 및 계산서 초기화
    recalculateFinalTotal();

    /******************************************************
     * 입금 현황 관련 로직
     ******************************************************/

    // 1. 페이지 로드 시 AJAX로 입금 내역 불러오기
    loadDepositHistory();

    // 2. '입금 현황 변경 내용 저장' 버튼 클릭 이벤트 (추가/수정)
    $('#saveDepositBtn').on('click', function() {
        const amount = $('#depositAmount').val();
        const depositDate = $('#depositDate').val();

        if (!amount) {
            alert('금액을 입력해주세요.');
            $('#depositAmount').focus();
            return;
        }
        if (!depositDate) {
            alert('입금일을 선택해주세요.');
            $('#depositDate').focus();
            return;
        }

        const depositSeq = $('#depositSeq').val();
        const depositData = {
            exhibitorSeq: exhibitorSeq,
            amount: parseInt(amount.replace(/,/g, '')) || 0,
            contentType: $('#depositContentType').val(),
            taxInvoiceStatus: $('#depositTaxStatus').val(),
            paymentStatus: $('#depositPaymentStatus').val(),
            depositDate: depositDate,
            scheduledDate: $('#depositScheduledDate').val() || null,
            depositorName: $('#depositDepositorName').val(),
            confirmerName: $('#depositConfirmerName').val(),
            comment: $('#depositComment').val()
        };

        let url, method;
        if (depositSeq) {
            url = '/mng/deposits/' + depositSeq;
            method = 'PUT';
            depositData.depositSeq = depositSeq;
        } else {
            url = '/mng/deposits';
            method = 'POST';
        }

        $.ajax({
            url: url,
            type: method,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(depositData),
            success: function(response) {
                if (response.resultCode === "0") {
                    alert(response.resultMsg);
                    if (method === 'POST' && response.data) {
                        $('#depositHistoryTable .no-data-row').remove();
                        addRowToDepositTable(response.data);
                        clearDepositForm();
                    } else {
                        location.reload();
                    }
                } else {
                    alert(response.resultMsg || '오류가 발생했습니다.');
                }
            },
            error: function() {
                alert('서버 통신 중 오류가 발생했습니다.');
            }
        });
    });

    // 3. '수정' 버튼 클릭 이벤트
    $('#depositHistoryTable').on('click', '.edit-btn', function() {
        const rowData = $(this).data('row');

        $('#depositSeq').val(rowData.depositSeq);
        $('#depositAmount').val(rowData.amount);
        $('#depositContentType').val(rowData.contentType);
        $('#depositTaxStatus').val(rowData.taxInvoiceStatus);
        $('#depositPaymentStatus').val(rowData.paymentStatus);
        $('#depositDate').val(rowData.depositDate ? rowData.depositDate.split('T')[0] : '');
        $('#depositScheduledDate').val(rowData.scheduledDate ? rowData.scheduledDate.split('T')[0] : '');
        $('#depositDepositorName').val(rowData.depositorName);
        $('#depositConfirmerName').val(rowData.confirmerName);
        $('#depositComment').val(rowData.comment);

        window.scrollTo(0, $('#depositForm').offset().top - 100);
    });

    // 4. '삭제' 버튼 클릭 이벤트
    $('#depositHistoryTable').on('click', '.delete-btn', function() {
        if (confirm('해당 항목을 정말 삭제하시겠습니까?')) {
            const depositSeq = $(this).data('seq');
            $.ajax({
                url: '/mng/deposits/' + depositSeq,
                type: 'POST', // DELETE 대신 POST 사용
                success: function(response) {
                    if (response.resultCode === "0") {
                        alert(response.resultMsg);
                        location.reload();
                    } else {
                        alert(response.resultMsg || '삭제 중 오류가 발생했습니다.');
                    }
                },
                error: function() {
                    alert('서버 통신 중 오류가 발생했습니다.');
                }
            });
        }
    });

    // 5. 입금 내역 관련 헬퍼 함수들
    function loadDepositHistory() {
        $.ajax({
            url: '/mng/deposits/' + exhibitorSeq,
            type: 'GET',
            success: function(historyList) {
                const tableBody = $('#depositHistoryTable tbody');
                tableBody.empty();
                if (historyList && historyList.length > 0) {
                    historyList.forEach(item => addRowToDepositTable(item));
                } else {
                    tableBody.append('<tr class="no-data-row"><td colspan="10" class="text-center">입금 내역이 없습니다.</td></tr>');
                }
            },
            error: function() {
                // 페이지 로드 시 데이터가 없는 경우가 많으므로 실패 alert는 주석 처리
                // alert('입금 내역을 불러오는 데 실패했습니다.');
            }
        });
    }

    function addRowToDepositTable(rowData) {
        const contentTypeDisplay = rowData.contentType || '-';
        const paymentStatusDisplay = rowData.paymentStatus || '-';
        const depositDateDisplay = rowData.depositDate ? rowData.depositDate.split('T')[0] : '-';
        const scheduledDateDisplay = rowData.scheduledDate ? rowData.scheduledDate.split('T')[0] : '-';
        const taxStatusDisplay = rowData.taxInvoiceStatus || '-';
        const depositorNameDisplay = rowData.depositorName || '-';
        const confirmerNameDisplay = rowData.confirmerName || '-';
        const commentDisplay = rowData.comment || '-';

        const row =
            '<tr class="text-center" id="depositRow-' + rowData.depositSeq + '">' +
            '<td>' + (rowData.amount || 0).toLocaleString() + ' 원</td>' +
            '<td>' + contentTypeDisplay + '</td>' +
            '<td>' + paymentStatusDisplay + '</td>' +
            '<td>' + depositDateDisplay + '</td>' +
            '<td>' + scheduledDateDisplay + '</td>' +
            '<td>' + taxStatusDisplay + '</td>' +
            '<td>' + depositorNameDisplay + '</td>' +
            '<td>' + confirmerNameDisplay + '</td>' +
            '<td>' + commentDisplay + '</td>' +
            '<td>' +
            '<button class="btn btn-sm btn-light-primary edit-btn">수정</button>' +
            '<button class="btn btn-sm btn-light-danger delete-btn" data-seq="' + rowData.depositSeq + '">삭제</button>' +
            '</td>' +
            '</tr>';

        const $row = $(row);
        $row.find('.edit-btn').data('row', rowData);
        $('#depositHistoryTable tbody').prepend($row);
    }

    function clearDepositForm() {
        $('#depositSeq').val('');
        $('#depositAmount').val('');
        $('#depositContentType').val('선금');
        $('#depositTaxStatus').val('미발행');
        $('#depositPaymentStatus').val('미납');
        $('#depositDate').val('');
        $('#depositScheduledDate').val('');
        $('#depositDepositorName').val('');
        $('#depositConfirmerName').val('');
        $('#depositComment').val('');
    }

    /******************************************************
     * 인보이스 생성 팝업 관련 로직
     ******************************************************/

    let myModalEl = document.getElementById('kt_modal_create_invoice');

    if(myModalEl){

        let myModal = new bootstrap.Modal('#kt_modal_create_invoice', {
            focus: true
        });

        myModalEl.addEventListener('hidden.bs.modal', event => {
            // input init
            $('#detailForm').removeAttr('src');
        })

        $('#createInvoiceConfirmBtn').on('click', function() {
            const invoiceType = $('#invoiceTypeSelect').val();

            const modal = bootstrap.Modal.getInstance(document.getElementById('kt_modal_create_invoice'));
            modal.hide();

            KTApp.showPageLoading();

            let ajaxUrl = "", pdfSavePath = "";
            if (invoiceType === 'booth') {
                ajaxUrl = '/mng/exhibitorNew/application/booth/invoice/insert.do';
                pdfSavePath = 'exhibitor/invoice/booth/' + exhibitorSeq;
            } else if (invoiceType === 'utility') {
                ajaxUrl = '/mng/exhibitorNew/application/utility/invoice/insert.do';
                pdfSavePath = 'exhibitor/invoice/utility/' + exhibitorSeq;
            } else {
                KTApp.hidePageLoading();
                return alert("잘못된 인보이스 종류입니다.");
            }

            $.ajax({
                url: ajaxUrl,
                method: 'POST',
                data: JSON.stringify({ exSeq: exhibitorSeq }),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data.resultCode === "0") {
                        const param = {
                            seq: data.customValue,
                            companyNameKo: $('#createInvoiceConfirmBtn').data('company-name')
                        };

                        createAndUploadPdfFromIframe(param, 'detailForm', pdfSavePath, invoiceType)
                            .then(() => {
                                KTApp.hidePageLoading();
                                Swal.fire({
                                    icon: 'info',
                                    title: '[ 인보이스 ]',
                                    text: '인보이스가 생성되었습니다.',
                                    confirmButtonText: '확인'
                                }).then(() => location.reload());
                            })
                            .catch(err => {
                                KTApp.hidePageLoading();
                                alert('PDF 생성 또는 업로드 중 오류가 발생했습니다.');
                            });
                    } else {
                        KTApp.hidePageLoading();
                        alert('인보이스 생성을 실패하였습니다: ' + data.resultMsg);
                    }
                },
                error: function() {
                    KTApp.hidePageLoading();
                    alert('서버 통신 중 오류가 발생했습니다.');
                }
            });
        });
    }//myModalEl

    /******************************************************
     * 인보이스 목록 관리 (삭제, 발송, 미리보기)
     ******************************************************/

    // 1. '삭제' 버튼 클릭 이벤트
    $('#deleteInvoicesBtn').on('click', function() {
        // 체크된 체크박스들을 찾습니다.
        const checkedInvoices = $('input[name="invoiceSeq"]:checked');

        if (checkedInvoices.length !== 1) {
            alert('삭제할 인보이스를 하나만 선택해주세요.');
            return;
        }

        const invoiceSeq = checkedInvoices.val();
        const invoiceType = checkedInvoices.data('type'); // 'booth' 또는 'utility'

        let deleteUrl = "";
        if (invoiceType === 'booth') {
            deleteUrl = '/mng/exhibitorNew/application/invoice/booth/delete.do';
        } else if (invoiceType === 'utility') {
            deleteUrl = '/mng/exhibitorNew/application/invoice/utility/delete.do';
        } else {
            alert('삭제할 수 없는 타입의 인보이스입니다.');
            return;
        }

        if (confirm('선택한 인보이스를 정말 삭제하시겠습니까?')) {
            $.ajax({
                url: deleteUrl,
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify([parseInt(invoiceSeq)]), // 서버가 배열을 기대하므로 배열에 담아 전송
                success: function(response) {
                    if (response.resultCode === "0") {
                        alert(response.resultMsg);

                        // --- ▼▼▼ 새로고침 전 스크롤 명령 저장 ▼▼▼ ---
                        sessionStorage.setItem('scrollToBottom', 'true');
                        location.reload();
                    } else {
                        alert(response.resultMsg || '삭제 중 오류가 발생했습니다.');
                    }
                },
                error: function() { alert('서버 통신 중 오류가 발생했습니다.'); }
            });
        }
    });

    // 2. '발송' 버튼 클릭 이벤트
    $('#sendInvoicesBtn').on('click', function() {
        const checkedInvoice = $('input[name="invoiceSeq"]:checked');

        if (checkedInvoice.length !== 1) {
            alert('발송할 인보이스를 하나만 선택해주세요.');
            return;
        }

        const invoiceSeq = checkedInvoice.val();
        const invoiceType = checkedInvoice.data('type');

        if (confirm('선택한 인보이스를 발송하시겠습니까?')) {
            KTApp.showPageLoading();

            // 1. 공통 정보인 참가업체 정보 조회 (기존 로직 활용)
            const exhibitorInfo = ajaxConnect('/mng/getExhibitorNewInfo.do', 'post', { seq: exhibitorSeq });

            if (exhibitorInfo) {
                const companyNameKo = exhibitorInfo.companyNameKo;
                const email = exhibitorInfo.email;
                const filePath = checkedInvoice.closest('.invoice-item').find('input[name="filePath"]').val();

                if (!filePath) {
                    KTApp.hidePageLoading();
                    alert('인보이스 파일 경로가 없습니다. PDF 생성 후 다시 시도해주세요.');
                    return;
                }

                // 2. 파일 경로 파싱
                const folderPath_r = filePath.replace('/usr/local/tomcat/webapps/','');
                const folderPath_s = folderPath_r.substring(0, folderPath_r.lastIndexOf('/') + 1);
                const fileName = filePath.substring(filePath.lastIndexOf('/') + 1);

                // 3. 인보이스 타입에 따라 메일 제목, gbn, 결과 업데이트 URL 분기
                let subject = '';
                let gbn = '';
                let updateUrl = '';

                if (invoiceType === 'booth') {
                    subject = `[KIBS 2026] ${companyNameKo} 전시부스 인보이스 발송`;
                    gbn = 'BOOTH';
                    updateUrl = '/mng/exhibitorNew/application/booth/invoice/mail/result/update.do';
                } else if (invoiceType === 'utility') {
                    subject = `[KIBS 2026] ${companyNameKo} 유틸리티 인보이스 발송`;
                    gbn = 'UTILITY';
                    updateUrl = '/mng/exhibitorNew/application/utility/invoice/mail/result/update.do';
                }

                // 4. 메일 발송 API에 보낼 데이터 구성
                const mailJson = {
                    subject: subject,
                    body: '', //템플릿 사용시 빈값
                    template: "161",
                    receiver: [{ email: email, note1: encodeURI(`https://kibs.com/mng/exhibitorNew/application/invoice/mail/open/update.do?gbn=${gbn}&seq=${invoiceSeq}`) }],
                    gbn: gbn,
                    folderPath: encodeURI(folderPath_s),
                    fileUrl: [{ name: encodeURI(fileName) }]
                };

                // 5. 메일 발송 실행
                const mailResult = ajaxConnect('/mail/send.do', 'post', mailJson);

                // 6. 발송 결과에 따라 DB 업데이트
                const sendStatus = (mailResult.resultCode === "0") ? '미열람' : '미발송';
                const sendResult = (mailResult.resultCode === "0") ? '발송성공' : '발송실패';

                const updateData = {
                    invoiceSeq: invoiceSeq,
                    sendStatus: sendStatus,
                    sendResult: sendResult,
                    sendResultMsg: mailResult.resultMessage
                };

                $.ajax({
                    url: updateUrl,
                    method: 'POST',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(updateData),
                    success: function (updateResponse) {
                        KTApp.hidePageLoading();
                        if (updateResponse.resultCode === "0") {
                            Swal.fire({
                                icon: 'info',
                                title: '[ 인보이스 ]',
                                text: '인보이스가 발송되었습니다.',
                                confirmButtonText: '확인'
                            }).then(() => {

                                // --- ▼▼▼ 새로고침 전 스크롤 명령 저장 ▼▼▼ ---
                                sessionStorage.setItem('scrollToBottom', 'true');
                                location.reload();
                            });
                        } else {
                            alert('메일은 발송되었으나, 발송 상태를 업데이트하는 데 실패했습니다.');
                        }
                    },
                    error: function() {
                        KTApp.hidePageLoading();
                        alert('발송 상태 업데이트 중 서버 통신 오류가 발생했습니다.');
                    }
                });

            } else {
                KTApp.hidePageLoading();
                alert('발송에 필요한 참가업체 정보를 찾지 못했습니다.');
            }
        }
    });

    const previewModalEl = document.getElementById('kt_modal_invoice_form');
    const previewModal = new bootstrap.Modal(previewModalEl);

    // '미리보기' 버튼 클릭 이벤트
    $('#previewBtn').on('click', function () {
        // 1. 체크된 인보이스를 찾습니다.
        const checkedInvoices = $('input[name="invoiceSeq"]:checked');

        if (checkedInvoices.length !== 1) {
            alert('미리보기할 인보이스를 하나만 선택해주세요.');
            return;
        }

        // 3. 선택된 인보이스의 서버 파일 경로(serverPath)를 가져옵니다.
        const serverPath = checkedInvoices.closest('.invoice-item').find('input[name="filePath"]').val();
        if (!serverPath) {
            alert('생성된 인보이스 파일이 없습니다. 먼저 인보이스를 생성해주세요.');
            return;
        }

        // --- 서버 전체 경로를 웹 URL로 변환 ---
        // '/usr/local/tomcat/webapps' 부분을 잘라내어 웹에서 접근 가능한 경로만 남깁니다.
        const webPath = serverPath.replace('/usr/local/tomcat/webapps', '');

        // --- PDF 뷰어 옵션을 URL에 추가 ▼▼▼ ---
        // #toolbar=0 : 상단 툴바 숨김
        // #navpanes=0 : 왼쪽 네비게이션(미리보기 탭) 숨김
        // #view=FitH : 가로 폭에 맞추기
        const previewUrl = webPath + '#toolbar=0&navpanes=0&view=FitH';

        // 모달 안의 iframe src 속성에 최종 URL을 설정합니다.
        $('#detailForm').attr('src', previewUrl);

        // 5. JavaScript로 직접 모달을 엽니다.
        previewModal.show();
    });

    // 모달이 닫힐 때, iframe의 내용을 비워서 리소스를 정리합니다.
    previewModalEl.addEventListener('hidden.bs.modal', function () {
        $('#detailForm').attr('src', 'about:blank'); // src를 비워줍니다.
    });
});

/**
 * [가정] PDF 생성 및 서버 업로드 함수
 * 이 함수는 이미 프로젝트 내에 존재하거나 별도로 관리된다고 가정합니다.
 * invoiceType에 따라 동적으로 동작하도록 수정된 최종 버전입니다.
 */
async function createAndUploadPdfFromIframe(param, iframeId, uploadPath, invoiceType) {
    const seq = param.seq;
    let companyNameKo = param.companyNameKo;
    companyNameKo = companyNameKo.replace(/[^a-zA-Z0-9ㄱ-힣]/g, '');

    let fileName = "";
    let viewUrl = "";
    let updateUrl = "";

    if (invoiceType === 'booth') {
        fileName = `invoice_${companyNameKo}_booth_${new Date().getTime()}`;
        viewUrl = `/mng/exhibitorNew/application/booth/invoice/detail.do?seq=${seq}`;
        updateUrl = '/mng/exhibitorNew/application/booth/invoice/filePath/update.do';
    } else if (invoiceType === 'utility') {
        fileName = `invoice_${companyNameKo}_utility_${new Date().getTime()}`;
        viewUrl = `/mng/exhibitorNew/application/utility/invoice/detail.do?seq=${seq}`;
        updateUrl = '/mng/exhibitorNew/application/utility/invoice/filePath/update.do';
    } else {
        alert("알 수 없는 인보이스 타입입니다.");
        return Promise.reject("Unknown invoice type");
    }

    const virtualFrame = document.createElement("iframe");
    try {
        const [htmlResponse, cssResponse] = await Promise.all([
            fetch(viewUrl, { mode: "cors" }),
            fetch('/css/invoice.css', { mode: "cors" })
        ]);
        const htmlText = await htmlResponse.text();
        const cssText = await cssResponse.text();

        virtualFrame.style.position = "absolute";
        virtualFrame.style.left = "-9999px";
        virtualFrame.style.border = "none";
        virtualFrame.style.width = '210mm';
        virtualFrame.style.height = 'auto';
        document.body.appendChild(virtualFrame);

        const doc = virtualFrame.contentDocument || virtualFrame.contentWindow.document;
        doc.open();
        doc.write(`<html><head><meta charset="utf-8"><style>${cssText}</style></head><body>${htmlText}</body></html>`);
        doc.close();

        await new Promise(resolve => {
            // 1. 폰트 로딩을 기다립니다.
            doc.fonts.ready.then(() => {
                // 2. 폰트 로딩이 끝나면 이미지 로딩을 체크합니다.
                const images = doc.images;
                if (images.length === 0) {
                    // 이미지가 없으면 바로 다음 단계로 진행
                    setTimeout(resolve, 200); // 아주 짧은 추가 렌더링 시간 확보
                    return;
                }

                let loadedCount = 0;
                const checkImages = () => {
                    if (loadedCount === images.length) {
                        setTimeout(resolve, 200); // 모든 이미지 로드 후 짧은 렌더링 시간 확보
                    }
                };

                Array.from(images).forEach(img => {
                    if (img.complete) {
                        loadedCount++;
                    } else {
                        img.onload = img.onerror = () => {
                            loadedCount++;
                            checkImages();
                        };
                    }
                });
                checkImages();
            });
        });

        const { jsPDF } = window.jspdf;
        const pdf = new jsPDF('p', 'mm', 'a4');
        const a4Width = pdf.internal.pageSize.getWidth();
        const a4Height = pdf.internal.pageSize.getHeight();

        const margin = 15;
        const contentWidth = a4Width - (margin * 2);

        // --- ▼▼▼ [핵심] '블록 조합' 방식 페이지 분할 로직 ▼▼▼ ---

        // 1. 모든 내용 블록을 각각의 캔버스로 캡처합니다.
        const contentBlocks = Array.from(doc.querySelectorAll('.inv_wrap > div'));
        const canvasPromises = contentBlocks.map(block => html2canvas(block, { scale: 2, useCORS: true, backgroundColor: '#FFFFFF' }));
        const canvases = await Promise.all(canvasPromises);

        let currentY = margin; // PDF 페이지 내에서 현재 그릴 위치 (y 좌표)

        // 2. 캡처된 캔버스들을 순회하며 PDF 페이지에 배치합니다.
        for (let i = 0; i < canvases.length; i++) {
            const canvas = canvases[i];
            const scaledImgHeight = (canvas.height * contentWidth) / canvas.width;

            // 3. 현재 페이지에 남은 공간이 이 이미지를 그리기에 부족한지 확인합니다.
            if (currentY + scaledImgHeight > (a4Height - margin)) {
                pdf.addPage(); // 공간이 없으면 새 페이지 추가
                currentY = margin; // y 좌표 초기화
            }

            // 4. PDF 페이지에 이미지 조각을 추가합니다.
            pdf.addImage(canvas.toDataURL('image/jpeg', 0.9), 'JPEG', margin, currentY, contentWidth, scaledImgHeight);

            // 5. 다음 이미지가 그려질 위치를 업데이트합니다.
            currentY += scaledImgHeight + 5;
        }

        // --- ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ ---

        const pdfBlob = pdf.output('blob');
        const formData = new FormData();
        formData.append('uploadFile', pdfBlob, fileName + '.pdf');

        const uploadResponse = await fetch('/file/upload.do?gbn=' + uploadPath, {
            method: 'POST',
            body: formData,
        });

        if (uploadResponse.ok) {
            const result = await uploadResponse.json();
            const filePath = result.uploadPath + '/' + result.fileName;

            await $.ajax({
                url: updateUrl,
                method: 'POST',
                data: JSON.stringify({ invoiceSeq: seq, filePath: filePath }),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8'
            });
        } else {
            throw new Error(`PDF 업로드 실패: ${uploadResponse.status}`);
        }
    } catch (error) {
        console.error('PDF 생성 또는 업로드 중 오류 발생:', error);
        throw error;
    } finally {
        if (virtualFrame && document.body.contains(virtualFrame)) {
            document.body.removeChild(virtualFrame);
        }
    }
}

function f_exhibitor_invoice_detail(seq){
    let hiddenField_seq = document.createElement('input');
    hiddenField_seq.type = 'hidden';
    hiddenField_seq.name = 'seq';
    hiddenField_seq.value = seq;

    let sendForm = document.createElement('form');
    sendForm.method = 'POST';
    sendForm.appendChild(hiddenField_seq);

    document.body.appendChild(sendForm);
    sendForm.action = '/mng/exhibitorNew/participant/company/invoice/detail.do';

    sendForm.submit();
}

function f_application_booth_new_modify_init_set(seq){
    window.location.href = '/mng/exhibitorNew/application/booth/detail.do?seq=' + seq;
}

function f_application_utility_new_modify_init_set(id){
    window.location.href = '/mng/exhibitorNew/application/utility/detail.do?seq=' + id;
}