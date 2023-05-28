import subprocess

def check_code_status(branch):
    result = subprocess.run(['git', 'status'], capture_output=True, text=True)
    output = result.stdout

    if branch == 'develop':
        if 'Your branch is ahead of' in output:
            print('A branch "develop" não está atualizada com a branch "master".')
            return False
    elif branch == 'master':
        if 'Your branch is behind' in output:
            print('A branch "master" não está atualizada.')
            return False
    else:
        if 'Your branch is behind' in output:
            print(f'A branch "{branch}" não está atualizada com a branch "develop".')
            return False

    return True

def check_commit_syntax(commit_message):
    if not commit_message.startswith('feat(') or not commit_message.endswith(')'):
        print('A sintaxe do commit está incorreta. Utilize o padrão "feat(branch): Mensagem".')
        return False

    return True

def main():
    result = subprocess.run(['git', 'symbolic-ref', '--short', 'HEAD'], capture_output=True, text=True)
    branch = result.stdout.strip()

    result = subprocess.run(['git', 'log', '-1', '--pretty=format:%s'], capture_output=True, text=True)
    commit_message = result.stdout.strip()

    if not check_code_status(branch):
        return

    if not check_commit_syntax(commit_message):
        return

    print(f'O código está atualizado com a branch "{branch}" e a sintaxe do commit está correta.')

if __name__ == '__main__':
    main()
