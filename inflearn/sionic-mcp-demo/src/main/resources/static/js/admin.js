// URL 단축기 관리자 앱
class UrlShortenerAdmin {
    constructor() {
        this.apiBase = '/api';
        this.urlForm = document.getElementById('url-form');
        this.urlInput = document.getElementById('url-input');
        this.submitBtn = document.getElementById('submit-btn');
        this.messageDiv = document.getElementById('message');
        this.urlList = document.getElementById('url-list');
        this.loadingDiv = document.getElementById('loading');
        this.emptyMessage = document.getElementById('empty-message');
        this.totalCount = document.getElementById('total-count');
        
        this.init();
    }
    
    init() {
        // 이벤트 리스너 등록
        this.urlForm.addEventListener('submit', (e) => this.handleSubmit(e));
        
        // 페이지 로드 시 URL 목록 불러오기
        this.loadUrls();
        
        console.log('URL 단축기 관리자 앱이 초기화되었습니다.');
    }
    
    async handleSubmit(e) {
        e.preventDefault();
        
        const url = this.urlInput.value.trim();
        if (!url) {
            this.showMessage('URL을 입력해주세요.', true);
            return;
        }
        
        if (!this.isValidUrl(url)) {
            this.showMessage('올바른 URL 형식을 입력해주세요.', true);
            return;
        }
        
        this.setLoading(true);
        
        try {
            const response = await fetch(`${this.apiBase}/shorten`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ originalUrl: url })
            });
            
            if (response.ok) {
                const result = await response.json();
                this.showMessage(`URL이 성공적으로 단축되었습니다! 단축 키: ${result.shortKey}`, false);
                this.urlInput.value = '';
                this.loadUrls(); // 목록 새로고침
            } else {
                const error = await response.json();
                this.showMessage(`URL 단축에 실패했습니다: ${error.message || '알 수 없는 오류'}`, true);
            }
        } catch (error) {
            console.error('URL 단축 오류:', error);
            this.showMessage('URL 단축 중 오류가 발생했습니다.', true);
        } finally {
            this.setLoading(false);
        }
    }
    
    async loadUrls() {
        this.showLoading(true);
        
        try {
            const response = await fetch(`${this.apiBase}/shorten/urls`);
            
            if (response.ok) {
                const urls = await response.json();
                this.renderUrlList(urls);
                this.updateStats(urls.length);
            } else {
                console.error('URL 목록 로드 실패:', response.status);
                this.showEmptyMessage('URL 목록을 불러오는데 실패했습니다.');
            }
        } catch (error) {
            console.error('URL 목록 로드 오류:', error);
            this.showEmptyMessage('URL 목록을 불러오는데 실패했습니다.');
        } finally {
            this.showLoading(false);
        }
    }
    
    async deleteUrl(shortKey) {
        if (!confirm('정말 삭제하시겠습니까?')) {
            return;
        }
        
        try {
            const response = await fetch(`${this.apiBase}/shorten/${shortKey}`, {
                method: 'DELETE'
            });
            
            if (response.ok) {
                this.showMessage(`URL이 성공적으로 삭제되었습니다. (키: ${shortKey})`, false);
                this.loadUrls(); // 목록 새로고침
            } else {
                this.showMessage('URL 삭제에 실패했습니다.', true);
            }
        } catch (error) {
            console.error('URL 삭제 오류:', error);
            this.showMessage('URL 삭제 중 오류가 발생했습니다.', true);
        }
    }
    
    renderUrlList(urls) {
        this.urlList.innerHTML = '';
        
        if (urls.length === 0) {
            this.showEmptyMessage('등록된 URL이 없습니다.');
            return;
        }
        
        this.hideEmptyMessage();
        
        urls.forEach((url, index) => {
            const urlItem = this.createUrlItem(url, index);
            this.urlList.appendChild(urlItem);
        });
    }
    
    createUrlItem(url, index) {
        const item = document.createElement('div');
        item.className = 'url-item';
        item.style.animationDelay = `${index * 0.1}s`;
        
        const shortUrl = `${window.location.origin}/api/redirect/${url.shortKey}`;
        
        item.innerHTML = `
            <div class="url-item-content">
                <div class="url-info">
                    <div>
                        <strong>단축 키:</strong>
                        <span class="short-key">${url.shortKey}</span>
                    </div>
                    <div class="original-url">
                        <strong>원본 URL:</strong>
                        <a href="${url.originalUrl}" target="_blank" title="${url.originalUrl}">
                            ${this.truncateUrl(url.originalUrl, 60)}
                        </a>
                    </div>
                    <div class="short-url">
                        <strong>단축 URL:</strong>
                        <a href="${shortUrl}" target="_blank" title="${shortUrl}">
                            ${shortUrl}
                        </a>
                    </div>
                    <div class="created-at">
                        <strong>생성 시간:</strong>
                        <span>${this.formatDate(url.createdAt)}</span>
                    </div>
                </div>
                <div class="url-actions">
                    <button class="delete-btn" onclick="app.deleteUrl('${url.shortKey}')">
                        삭제
                    </button>
                </div>
            </div>
        `;
        
        return item;
    }
    
    setLoading(loading) {
        this.submitBtn.disabled = loading;
        this.submitBtn.textContent = loading ? '처리 중...' : '단축하기';
    }
    
    showLoading(show) {
        this.loadingDiv.style.display = show ? 'block' : 'none';
        if (show) {
            this.urlList.innerHTML = '';
            this.hideEmptyMessage();
        }
    }
    
    showEmptyMessage(message = '등록된 URL이 없습니다.') {
        this.emptyMessage.textContent = message;
        this.emptyMessage.style.display = 'block';
    }
    
    hideEmptyMessage() {
        this.emptyMessage.style.display = 'none';
    }
    
    showMessage(message, isError = false) {
        this.messageDiv.textContent = message;
        this.messageDiv.className = `message ${isError ? 'error' : 'success'} show`;
        
        // 3초 후 메시지 숨기기
        setTimeout(() => {
            this.messageDiv.classList.remove('show');
        }, 3000);
    }
    
    updateStats(count) {
        this.totalCount.textContent = count;
    }
    
    isValidUrl(string) {
        try {
            new URL(string);
            return true;
        } catch (_) {
            return false;
        }
    }
    
    truncateUrl(url, maxLength) {
        if (url.length <= maxLength) return url;
        return url.substring(0, maxLength - 3) + '...';
    }
    
    formatDate(dateString) {
        try {
            const date = new Date(dateString);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            const hours = String(date.getHours()).padStart(2, '0');
            const minutes = String(date.getMinutes()).padStart(2, '0');
            
            return `${year}-${month}-${day} ${hours}:${minutes}`;
        } catch (error) {
            return dateString;
        }
    }
}

// 앱 초기화
let app;
document.addEventListener('DOMContentLoaded', () => {
    app = new UrlShortenerAdmin();
});
